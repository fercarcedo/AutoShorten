package fergaral.autoshorten.listeners

import android.content.ClipboardManager
import android.content.Context
import fergaral.autoshorten.util.UrlShortener
import fergaral.autoshorten.util.copyText
import fergaral.autoshorten.util.getClipboardText
import fergaral.autoshorten.util.isURL
import org.jetbrains.anko.doAsync

/**
 * Created by Fer on 28/01/2018.
 */
class ClipboardListener(private val clipboardManager: ClipboardManager,
                        private val context: Context): ClipboardManager.OnPrimaryClipChangedListener {
    override fun onPrimaryClipChanged() {
        val clipboardText = clipboardManager.getClipboardText(context)

        if (clipboardText != null && clipboardText.isURL()) {
            doAsync {
                val url = UrlShortener().shortenUrl(clipboardText)
                clipboardManager.copyText(url)
            }
        }
    }
}