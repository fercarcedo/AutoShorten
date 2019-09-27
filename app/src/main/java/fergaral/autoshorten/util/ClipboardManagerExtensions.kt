package fergaral.autoshorten.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * Created by Fer on 29/01/2018.
 */
fun ClipboardManager.getClipboardText(context: Context): String? {
    if (hasPrimaryClip()) {
        val clip = primaryClip
        if (clip != null && clip.itemCount > 0) {
            val clipboardText = clip.getItemAt(0).coerceToText(context)
            if (clipboardText != null)
                return clipboardText.toString()
        }
    }
    return null
}

fun ClipboardManager.copyText(text: String) {
    val clipData = ClipData.newPlainText(text, text)
    setPrimaryClip(clipData)
}