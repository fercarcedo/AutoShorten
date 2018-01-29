package fergaral.autoshorten.ui

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import fergaral.autoshorten.R
import fergaral.autoshorten.util.*
import kotlinx.android.synthetic.main.activity_shorten_url.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync

/**
 * Created by Fer on 29/01/2018.
 */
class ShortenUrlActivity: AppCompatActivity() {
    private val clipboardManager by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shorten_url)

        etURL.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                btnShorten.isEnabled = !s.isNullOrEmpty()
            }
        })
    }

    override fun onBackPressed() {
        dismiss(null)
    }

    fun dismiss(view: View?) {
        setResult(Activity.RESULT_CANCELED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            finishAfterTransition()
        else
            finish()
    }

    fun shorten(view: View?) {
        val longUrl = etURL.text.toString()
        if (longUrl.isURL()) {
            pbShorten.show()
            doAsync(exceptionHandler = { _ ->
                Utils.showShortenError(this, { dismiss(null) })
            }) {
                val shortUrl = UrlShortener().shortenUrl(longUrl)
                activityUiThread {
                    clipboardManager.copyText(shortUrl)
                    Toast.makeText(this@ShortenUrlActivity, getString(R.string.shortened_url_copied), Toast.LENGTH_SHORT).show()
                    dismiss(null)
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.url_format_incorrect), Toast.LENGTH_SHORT).show()
        }
    }
}