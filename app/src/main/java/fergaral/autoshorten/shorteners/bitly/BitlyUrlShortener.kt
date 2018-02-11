package fergaral.autoshorten.shorteners.bitly

import fergaral.autoshorten.BuildConfig
import fergaral.autoshorten.shorteners.UrlShortener
import fergaral.autoshorten.shorteners.bitly.service.BitlyServiceFactory

/**
 * Created by Fer on 08/02/2018.
 */
class BitlyUrlShortener : UrlShortener() {
    override fun doShorten(longUrl: String): String {
        val bitlyService = BitlyServiceFactory.getBitlyService()
        val bitlyResult = bitlyService.shorten(BuildConfig.BITLY_API_KEY, longUrl).execute().body()

        if (bitlyResult != null)
            return bitlyResult.data.url
        throw Exception("Error shortening URL")
    }
}