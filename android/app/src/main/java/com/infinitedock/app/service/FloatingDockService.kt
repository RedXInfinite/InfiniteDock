package com.infinitedock.app.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.infinitedock.app.MainActivity
import com.infinitedock.app.database.AppDatabase
import com.infinitedock.app.database.AppRepository
import com.infinitedock.app.theme.ThemeRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FloatingDockService : Service() {

    private var windowManager: WindowManager? = null
    private var dockView: View? = null
    private var layoutParams: WindowManager.LayoutParams? = null

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private lateinit var repository: AppRepository

    override fun onCreate() {
        super.onCreate()
        repository = AppRepository(AppDatabase.getDatabase(this))
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())

        if (Settings.canDrawOverlays(this)) {
            initFloatingDockView()
            observePreferencesAndShortcuts()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "InfiniteDock Overlay Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps the InfiniteDock floating dock active over apps"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("InfiniteDock Active")
            .setContentText("Floating Dock overlay is running")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun initFloatingDockView() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val dockContainer = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(20, 16, 20, 16)
            setBackgroundColor(0xDD111823.toInt())
        }

        layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 300
        }

        // Add drag support
        dockContainer.setOnTouchListener(object : View.OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val params = layoutParams ?: return false
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager?.updateViewLayout(dockView, params)
                        return true
                    }
                }
                return false
            }
        })

        dockView = dockContainer
        windowManager?.addView(dockView, layoutParams)
    }

    private fun observePreferencesAndShortcuts() {
        serviceScope.launch {
            repository.preferences.collectLatest { prefs ->
                if (prefs != null) {
                    val theme = ThemeRegistry.getThemeById(prefs.selectedThemeId)
                    val sizePx = (prefs.dockSizeDp * resources.displayMetrics.density).toInt()
                    dockView?.let { view ->
                        view.alpha = prefs.dockAlpha
                        view.minimumHeight = sizePx
                        // Update background color based on active theme primary/surface
                        view.setBackgroundColor(
                            (theme.surfaceColor.run {
                                val a = (prefs.dockAlpha * 255).toInt() and 0xFF
                                val r = (red * 255).toInt() and 0xFF
                                val g = (green * 255).toInt() and 0xFF
                                val b = (blue * 255).toInt() and 0xFF
                                (a shl 24) or (r shl 16) or (g shl 8) or b
                            })
                        )
                    }
                }
            }
        }

        serviceScope.launch {
            repository.dockShortcuts.collectLatest { shortcuts ->
                val container = dockView as? LinearLayout ?: return@collectLatest
                container.removeAllViews()

                if (shortcuts.isEmpty()) {
                    val tv = TextView(this@FloatingDockService).apply {
                        text = "❖ DOCK"
                        setTextColor(0xFFFFE500.toInt())
                        setPadding(12, 8, 12, 8)
                    }
                    container.addView(tv)
                } else {
                    for (shortcut in shortcuts) {
                        val iconView = TextView(this@FloatingDockService).apply {
                            text = shortcut.displayName.firstOrNull()?.uppercase() ?: "A"
                            textSize = 16f
                            setTextColor(0xFFFFE500.toInt())
                            setPadding(16, 12, 16, 12)
                            setOnClickListener {
                                launchApp(shortcut.packageName)
                            }
                        }
                        container.addView(iconView)
                    }
                }
            }
        }
    }

    private fun launchApp(packageName: String) {
        try {
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(launchIntent)
            } else {
                Toast.makeText(this, "App $packageName not installed", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Could not open $packageName", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        if (dockView != null && windowManager != null) {
            try {
                windowManager?.removeView(dockView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        private const val CHANNEL_ID = "infinite_dock_overlay_channel"
        private const val NOTIFICATION_ID = 1001

        fun startService(context: Context) {
            val intent = Intent(context, FloatingDockService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stopService(context: Context) {
            val intent = Intent(context, FloatingDockService::class.java)
            context.stopService(intent)
        }
    }
}
