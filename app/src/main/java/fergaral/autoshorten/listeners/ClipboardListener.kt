package fergaral.autoshorten.listeners

import android.content.ClipboardManager
import android.content.Context
import fergaral.autoshorten.data.repository.DomainRepository
import fergaral.autoshorten.shorteners.UrlShortenerFactory
import fergaral.autoshorten.util.Utils
import fergaral.autoshorten.util.copyText
import fergaral.autoshorten.util.getClipboardText
import fergaral.autoshorten.util.isURL
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by Fer on 28/01/2018.
 */
class ClipboardListener @Inject constructor(private val context: Context,
                                            private val repository: DomainRepository): ClipboardManager.OnPrimaryClipChangedListener {
    private val clipboardManager by lazy {
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun onPrimaryClipChanged() {
        val clipboardText = clipboardManager.getClipboardText(context)
        shortenUrl(clipboardText)
    }

    fun shortenUrl(clipboardText: String?) {
        if (clipboardText != null && clipboardText.isURL()) {
            doAsync {
                if (shortenUrl(repository, Utils.toDomain(clipboardText))) {
                    val url = UrlShortenerFactory.getUrlShortener(context).shortenUrl(clipboardText)
                    clipboardManager.copyText(url)
                }
            }
        }
    }

    private fun shortenUrl(repository: DomainRepository, domainUrl: String): Boolean {
        return !repository.hasDomains() || repository.exists(domainUrl)
    }
}