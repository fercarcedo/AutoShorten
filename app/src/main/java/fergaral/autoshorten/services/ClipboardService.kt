package fergaral.autoshorten.services

import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import dagger.android.AndroidInjection
import fergaral.autoshorten.listeners.ClipboardListener
import fergaral.autoshorten.ui.MainActivity
import fergaral.autoshorten.util.NotificationHelper
import javax.inject.Inject


class ClipboardService : Service() {
    private val clipboardManager by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    @Inject
    lateinit var clipboardListener: ClipboardManager.OnPrimaryClipChangedListener

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        startForeground()
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
