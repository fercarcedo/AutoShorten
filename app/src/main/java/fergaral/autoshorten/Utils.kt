package fergaral.autoshorten

import android.content.Context
import android.widget.Toast

/**
 * Created by Fer on 29/01/2018.
 */
class Utils {
    companion object {
        fun showShortenError(context: Context) {
            Toast.makeText(context, context.getString(R.string.shorten_error), Toast.LENGTH_SHORT).show()
        }
    }
}