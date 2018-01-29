package fergaral.autoshorten

import android.content.ClipboardManager
import org.jetbrains.anko.doAsync

/**
 * Created by Fer on 28/01/2018.
 */
class ClipboardListener(private val clipboardManager: ClipboardManager): ClipboardManager.OnPrimaryClipChangedListener {
    override fun onPrimaryClipChanged() {
        val clipboardText = clipboardManager.getClipboardText()

        if (clipboardText.isURL()) {
            doAsync {
                val url = UrlShortener().shortenUrl(clipboardText)
                clipboardManager.copyText(url)
            }
        }
    }
}