package fergaral.autoshorten.ui

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import fergaral.autoshorten.R
import fergaral.autoshorten.data.Domain
import fergaral.autoshorten.data.DomainExistsException
import fergaral.autoshorten.data.repository.DomainRepository
import fergaral.autoshorten.util.Utils
import kotlinx.android.synthetic.main.activity_url.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class DomainActivity : UrlActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DomainsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DomainsViewModel::class.java)
        dialog_title.text = getString(R.string.new_domain)
    }

    override fun handleDialogClick(url: String) {
        doAsync {
            try {
                viewModel.insertDomain(Utils.toDomain(url))
            } catch (e: DomainExistsException) {
                activityUiThread {
                    Toast.makeText(this@DomainActivity, R.string.domain_already_exists, Toast.LENGTH_LONG).show()
                }
            }
            activityUiThread {
                dismiss(null)
            }
        }
    }
}
