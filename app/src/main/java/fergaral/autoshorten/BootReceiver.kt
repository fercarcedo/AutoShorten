package fergaral.autoshorten

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Fer on 28/01/2018.
 */
class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action in arrayOf("android.intent.action.BOOT_COMPLETED",
                        "android.intent.action.QUICKBOOT_POWERON")) {
            ClipboardService.startIfNecessary(context)
        }
    }
}