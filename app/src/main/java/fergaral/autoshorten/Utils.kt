package fergaral.autoshorten

import android.app.Activity
import android.widget.Toast

/**
 * Created by Fer on 29/01/2018.
 */
class Utils {
    companion object {
        fun showShortenError(activity: Activity, handler: () -> Unit) {
            activity.runOnUiThread {
                handler()
                Toast.makeText(activity, activity.getString(R.string.shorten_error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}