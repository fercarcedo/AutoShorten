package fergaral.autoshorten.ui

import android.app.ActivityOptions
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.ShareCompat
import android.util.Log
import android.widget.Toast
import fergaral.autoshorten.R
import fergaral.autoshorten.services.ClipboardService
import fergaral.autoshorten.util.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            ClipboardService.startIfNecessary(this)

        checkSharedContent(intent)

        swShortenCopy.isChecked = PreferenceManager.getDefaultSharedPreferences(this)
                                                   .getBoolean(SHORTEN_COPY_KEY, true)

        swShortenCopy.setOnCheckedChangeListener { _, checked ->
            PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
                    .edit()
                    .putBoolean(SHORTEN_COPY_KEY, checked)
                    .apply()

            if (checked)
                ClipboardService.startIfNecessary(this@MainActivity)
            else
                stopService(Intent(this@MainActivity, ClipboardService::class.java))
        }

        fab.setOnClickListener { fabClick() }
    }

    private fun fabClick() {
        val intent = Intent(this, ShortenUrlActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                fab, getString(R.string.transition_shorten_url))
        startActivity(intent, options.toBundle())
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            checkSharedContent(intent)
        }
    }

    private fun checkSharedContent(intent: Intent) {
        if (Intent.ACTION_SEND == intent.action) {
            handleSendText(intent)
        }
    }

    private fun handleSendText(intent: Intent) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null && sharedText.isURL()) {
            showProgress()
            doAsync(exceptionHandler = { _ ->
                Utils.showShortenError(this, { hideProgress() })
            }) {
                val shortUrl = UrlShortener().shortenUrl(sharedText)
                activityUiThread {
                    hideProgress()
                    val shareIntent = ShareCompat.IntentBuilder.from(this@MainActivity)
                            .setChooserTitle(getString(R.string.share_shorten_url))
                            .setType("text/plain")
                            .setText(shortUrl)
                            .intent
                    if (shareIntent.resolveActivity(packageManager) != null)
                        startActivity(shareIntent)
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.url_format_incorrect), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgress() {
        mainLayout.hide()
        fab.hide()
        progressBar.show()
    }

    private fun hideProgress() {
        progressBar.hide()
        mainLayout.show()
        fab.show()
    }

    companion object {
        val SHORTEN_COPY_KEY = "shorten_copy"
    }
}
