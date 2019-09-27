package fergaral.autoshorten.ui

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import fergaral.autoshorten.R
import fergaral.autoshorten.data.repository.DomainRepository
import fergaral.autoshorten.shorteners.UrlShortenerFactory
import fergaral.autoshorten.util.Utils
import fergaral.autoshorten.util.copyText
import fergaral.autoshorten.util.getClipboardText
import fergaral.autoshorten.util.isURL
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class ShortenUrlFloatingActivity : AppCompatActivity() {

    private val clipboardManager by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_shorten_url_floating)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            clipboardManager.getClipboardText(this)?.let { text ->
                if (text.isURL()) {
                    doAsync(exceptionHandler = {
                        Utils.showShortenError(this, it) { finish() }
                    }) {
                        val shortUrl = UrlShortenerFactory.getUrlShortener(this@ShortenUrlFloatingActivity).shortenUrl(text)
                        activityUiThread {
                            clipboardManager.copyText(shortUrl)
                            finish()
                        }
                    }
                } else {
                    finish()
                }
            } ?: finish()
        }
    }
}