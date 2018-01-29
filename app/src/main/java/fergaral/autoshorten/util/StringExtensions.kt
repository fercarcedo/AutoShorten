package fergaral.autoshorten.util

import android.util.Patterns

/**
 * Created by Fer on 29/01/2018.
 */
fun String.isURL(): Boolean {
    return Patterns.WEB_URL.matcher(this).matches()
}