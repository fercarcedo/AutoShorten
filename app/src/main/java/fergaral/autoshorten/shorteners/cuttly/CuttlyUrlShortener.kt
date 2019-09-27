package fergaral.autoshorten.shorteners.cuttly

import fergaral.autoshorten.BuildConfig
import fergaral.autoshorten.shorteners.UrlShortener
import fergaral.autoshorten.shorteners.cuttly.service.CuttlyServiceFactory

/**
 * Created by Fer on 08/02/2018.
 */
class CuttlyUrlShortener : UrlShortener() {
    override fun doShorten(longUrl: String): String {
        val cuttlyService = CuttlyServiceFactory.getCuttlyService()
        val cuttlyResult = cuttlyService.shorten(BuildConfig.CUTTLY_API_KEY, longUrl).execute().body()

        if (cuttlyResult != null)
            return cuttlyResult.url.shortLink
        throw Exception("Error shortening URL")
    }
}