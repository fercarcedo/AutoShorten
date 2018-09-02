package fergaral.autoshorten.util

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import fergaral.autoshorten.R
import fergaral.autoshorten.ui.MainActivity

/**
 * Created by Fer on 27/01/2018.
 */
internal class NotificationHelper(ctx: Context): ContextWrapper(ctx) {
    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannels() {
        val mainChannel = NotificationChannel(MAIN_CHANNEL,
                getString(R.string.main_channel),
                NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(mainChannel)
    }

    fun getClipboardNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        return NotificationCompat.Builder(this, MAIN_CHANNEL)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.clipboard_notification_text))
                .setTicker(getString(R.string.clipboard_notification_ticker))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setAutoCancel(true)
                .build()
    }

    companion object {
        val MAIN_CHANNEL = "main_channel"
        val CLIPBOARD_NOTIFICATION_ID = 1
    }
}