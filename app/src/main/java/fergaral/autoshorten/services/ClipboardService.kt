package fergaral.autoshorten.services

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.Patterns
import fergaral.autoshorten.MainActivity
import fergaral.autoshorten.NotificationHelper
import fergaral.autoshorten.listeners.ClipboardListener
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ClipboardService : Service() {
    private val clipboardManager by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    private var clipboardListener: ClipboardManager.OnPrimaryClipChangedListener? = null

    override fun onCreate() {
        super.onCreate()
        startForeground()
        clipboardListener = ClipboardListener(clipboardManager)
        clipboardManager.addPrimaryClipChangedListener(clipboardListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        clipboardManager.removePrimaryClipChangedListener(clipboardListener)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun startForeground() {
        val notification = NotificationHelper(this).getClipboardNotification()
        startForeground(NotificationHelper.CLIPBOARD_NOTIFICATION_ID, notification)
    }

    companion object {
        fun startIfNecessary(context: Context) {
            val shortenCopy = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(MainActivity.SHORTEN_COPY_KEY, true)
            if (shortenCopy) {
                ContextCompat.startForegroundService(context, Intent(context, ClipboardService::class.java))
            }
        }
    }
}
