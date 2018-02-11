package fergaral.autoshorten.util

import android.app.Activity
import android.widget.Toast
import fergaral.autoshorten.R
import java.net.URI

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

        fun toDomain(urlString: String): String {
            var url = urlString
            if (!url.startsWith("http://") &&
                    !url.startsWith("https://"))
                url = "http://" + url
            return URI(url).host
        }
    }
}