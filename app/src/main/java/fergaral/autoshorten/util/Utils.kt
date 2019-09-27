package fergaral.autoshorten.util

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import fergaral.autoshorten.R
import fergaral.autoshorten.data.repository.DomainRepository
import fergaral.autoshorten.shorteners.UrlShortenerFactory
import org.jetbrains.anko.doAsync
import java.net.URI

/**
 * Created by Fer on 29/01/2018.
 */
class Utils {
    companion object {
        @JvmStatic
        fun showShortenError(activity: Activity, handler: () -> Unit) {
            activity.runOnUiThread {
                handler()
                Toast.makeText(activity, activity.getString(R.string.shorten_error), Toast.LENGTH_SHORT).show()
            }
        }

        @JvmStatic
        fun showShortenError(activity: Activity, t: Throwable, handler: () -> Unit) {
            activity.runOnUiThread {
                handler()
                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        @JvmStatic
        fun toDomain(urlString: String): String {
            var url = urlString
            if (!url.startsWith("http://") &&
                    !url.startsWith("https://"))
                url = "http://" + url

            var host = URI(url).host
            if (host.startsWith("www."))
                host = host.substring(4)
            return host
        }
    }
}