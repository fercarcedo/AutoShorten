package fergaral.autoshorten.shorteners

/**
 * Created by Fer on 28/01/2018.
 */
abstract class UrlShortener {
    fun shortenUrl(longUrl: String): String {
        var sourceUrl = longUrl
        if (!sourceUrl.startsWith("http"))
            sourceUrl = "http://" + sourceUrl

        val urlObject = java.net.URL(sourceUrl)
        if (urlObject.host in arrayOf("goo.gl", "bit.ly")) {
            return sourceUrl
        }

        return doShorten(sourceUrl)
    }

    abstract fun doShorten(longUrl: String): String;
}