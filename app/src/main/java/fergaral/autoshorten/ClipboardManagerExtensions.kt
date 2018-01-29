package fergaral.autoshorten

import android.content.ClipData
import android.content.ClipboardManager

/**
 * Created by Fer on 29/01/2018.
 */
fun ClipboardManager.getClipboardText(): String {
    return primaryClip.getItemAt(0).text.toString()
}

fun ClipboardManager.copyText(text: String) {
    val clipData = ClipData.newPlainText(text, text)
    primaryClip = clipData
}