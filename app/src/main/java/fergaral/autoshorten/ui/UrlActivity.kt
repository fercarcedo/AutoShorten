package fergaral.autoshorten.ui

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import fergaral.autoshorten.R
import fergaral.autoshorten.shorteners.UrlShortenerFactory
import fergaral.autoshorten.util.Utils
import fergaral.autoshorten.util.copyText
import fergaral.autoshorten.util.isURL
import fergaral.autoshorten.util.show
import kotlinx.android.synthetic.main.activity_url.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync

/**
 * Created by Fer on 10/02/2018.
 */
abstract class UrlActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url)

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
            handleDialogClick(longUrl)
        } else {
            Toast.makeText(this, getString(R.string.url_format_incorrect), Toast.LENGTH_SHORT).show()
        }
    }

    abstract fun handleDialogClick(url: String)
}