package fergaral.autoshorten

import android.util.Log
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.services.urlshortener.Urlshortener
import com.google.api.services.urlshortener.model.Url

/**
 * Created by Fer on 28/01/2018.
 */
class UrlShortener {
    fun shortenUrl(longUrl: String): String {
        var sourceUrl = longUrl
        if (!sourceUrl.startsWith("http"))
            sourceUrl = "http://" + sourceUrl

        val urlObject = java.net.URL(sourceUrl)
        if (urlObject.host in arrayOf("goo.gl", "bit.ly")) {
            return sourceUrl
        }

        val builder = Urlshortener.Builder(AndroidHttp.newCompatibleTransport(),
                AndroidJsonFactory.getDefaultInstance(), null)
        val urlshortener = builder.build()
        val url = Url()
        url.setLongUrl(sourceUrl)
        val insert = urlshortener.url().insert(url)
        insert.setKey("AIzaSyBEYT61xYr0mfFZE-lBAZFP8v8yRxFT9nE")
        return insert.execute().id
    }
}