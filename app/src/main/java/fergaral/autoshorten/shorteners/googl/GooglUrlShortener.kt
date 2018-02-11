package fergaral.autoshorten.shorteners.googl

import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.services.urlshortener.Urlshortener
import com.google.api.services.urlshortener.model.Url
import fergaral.autoshorten.BuildConfig
import fergaral.autoshorten.shorteners.UrlShortener

/**
 * Created by Fer on 08/02/2018.
 */
class GooglUrlShortener : UrlShortener() {
    override fun doShorten(longUrl: String): String {
        val builder = Urlshortener.Builder(AndroidHttp.newCompatibleTransport(),
                AndroidJsonFactory.getDefaultInstance(), null)
        val urlshortener = builder.build()
        val url = Url()
        url.longUrl = longUrl
        val insert = urlshortener.url().insert(url)
        insert.key = BuildConfig.GOOGL_API_KEY
        return insert.execute().id
    }
}