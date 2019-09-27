package fergaral.autoshorten.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.ShareCompat
import androidx.core.view.*
import fergaral.autoshorten.R
import fergaral.autoshorten.services.ClipboardService
import fergaral.autoshorten.shorteners.UrlShortenerFactory
import fergaral.autoshorten.util.Utils
import fergaral.autoshorten.util.hide
import fergaral.autoshorten.util.isURL
import fergaral.autoshorten.util.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.copy_url_card.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            ClipboardService.startIfNecessary(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            goEdgeToEdge()
        }

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
        chooseDomainsBtn.setOnClickListener { startActivity(Intent(this, DomainsToShortenActivity::class.java ))}
    }

    private fun goEdgeToEdge() {
        mainLayout.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        handleFabInsets()
        handleMainLayoutInsets()
    }

    private fun handleFabInsets() {
        val originalRightMargin = fab.marginRight
        val originalBottomMargin = fab.marginBottom
        ViewCompat.setOnApplyWindowInsetsListener(fab) { v, insets ->
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                rightMargin = originalRightMargin + insets.systemWindowInsetRight
                bottomMargin = originalBottomMargin + insets.systemWindowInsetBottom
            }
            insets
        }
    }

    private fun handleMainLayoutInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            v.updatePadding(
                    left = insets.systemWindowInsetLeft,
                    right = insets.systemWindowInsetRight,
                    top = insets.systemWindowInsetTop,
                    bottom = insets.systemWindowInsetBottom
            )
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        if (menu != null) {
            val item = menu.findItem(R.id.spShortener)
            val spinner = item.actionView as Spinner
            val spinnerItems = listOf("goo.gl", "bit.ly")
            val spinnerArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    spinnerItems)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item)
            spinner.adapter = spinnerArrayAdapter
            val selectedUrlShortener = PreferenceManager.getDefaultSharedPreferences(this).getString(UrlShortenerFactory.URL_SHORTENER_KEY, UrlShortenerFactory.URL_SHORTENER_DEFAULT)
            spinner.setSelection(spinnerItems.indexOf(selectedUrlShortener))
            spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    shortenProviderChosen(parent?.getItemAtPosition(position) as String)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }

        return true
    }

    private fun shortenProviderChosen(selectedItem: String) {
        val preferenceEditor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        preferenceEditor.putString(UrlShortenerFactory.URL_SHORTENER_KEY, selectedItem)
        preferenceEditor.apply()
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
        } else {
            showAndroidQInfoIfFirstUse()
        }
    }

    private fun showAndroidQInfoIfFirstUse() {
        val shownAndroidQInfo = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(ANDROIDQ_INFO, false)
        if (!shownAndroidQInfo) {

        }
    }

    private fun handleSendText(intent: Intent) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null && sharedText.isURL()) {
            showProgress()
            doAsync(exceptionHandler = {
                Utils.showShortenError(this) { hideProgress() }
            }) {
                val shortUrl = UrlShortenerFactory.getUrlShortener(this@MainActivity).shortenUrl(sharedText)
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
        val ANDROIDQ_INFO = "androidq_info"
    }
}
