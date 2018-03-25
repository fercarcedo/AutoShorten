package fergaral.autoshorten.shorteners

/**
 * Created by Fer on 28/01/2018.
 */
abstract class UrlShortener {
    fun shortenUrl(longUrl: String): String {
        var sourceUrl = longUrl
        if (!sourceUrl.startsWith("http://") &&
                !sourceUrl.startsWith("https://"))
            sourceUrl = "http://$sourceUrl"

        val urlObject = java.net.URL(sourceUrl)
        if (urlObject.host in arrayOf("goo.gl", "bit.ly")) {
            return sourceUrl
        }

        if (urlObject.host == "youtu.be") {
            val urlParts = sourceUrl.split("/")
            val videoKey = urlParts[urlParts.size - 1]
            return doShorten("https://www.youtube.com/watch?v=$videoKey")
        }

        return doShorten(sourceUrl)
    }

    abstract fun doShorten(longUrl: String): String;
}