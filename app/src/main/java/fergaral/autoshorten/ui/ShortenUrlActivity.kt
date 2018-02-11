package fergaral.autoshorten.ui

import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import fergaral.autoshorten.R
import fergaral.autoshorten.shorteners.UrlShortenerFactory
import fergaral.autoshorten.util.Utils
import fergaral.autoshorten.util.copyText
import fergaral.autoshorten.util.show
import kotlinx.android.synthetic.main.activity_url.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync

/**
 * Created by Fer on 29/01/2018.
 */
class ShortenUrlActivity: UrlActivity() {
    private val clipboardManager by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun handleDialogClick(url: String) {
        pbShorten.show()
        doAsync(exceptionHandler = { _ ->
            Utils.showShortenError(this, { dismiss(null) })
        }) {
            val shortUrl = UrlShortenerFactory.getUrlShortener(this@ShortenUrlActivity).shortenUrl(url)
            activityUiThread {
                clipboardManager.copyText(shortUrl)
                Toast.makeText(this@ShortenUrlActivity, getString(R.string.shortened_url_copied), Toast.LENGTH_SHORT).show()
                dismiss(null)
            }
        }
    }
}