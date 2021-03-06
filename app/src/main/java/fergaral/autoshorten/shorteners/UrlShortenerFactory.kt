package fergaral.autoshorten.shorteners

import android.content.Context
import android.preference.PreferenceManager
import fergaral.autoshorten.shorteners.bitly.BitlyUrlShortener
import fergaral.autoshorten.shorteners.cuttly.CuttlyUrlShortener

/**
 * Created by Fer on 08/02/2018.
 */
class UrlShortenerFactory {
    companion object {
        val URL_SHORTENER_KEY = "url_shortener"
        val URL_SHORTENER_DEFAULT = "cutt.ly"

        fun getUrlShortener(context: Context): UrlShortener {
            val shortener = PreferenceManager.getDefaultSharedPreferences(context).getString(URL_SHORTENER_KEY, URL_SHORTENER_DEFAULT)
            if (shortener == "bit.ly")
                return BitlyUrlShortener()
            return CuttlyUrlShortener()
        }
    }
}