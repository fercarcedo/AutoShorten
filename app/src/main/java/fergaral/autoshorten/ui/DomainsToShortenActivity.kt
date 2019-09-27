package fergaral.autoshorten.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import fergaral.autoshorten.R
import fergaral.autoshorten.data.Domain
import fergaral.autoshorten.di.DaggerAppComponent
import fergaral.autoshorten.util.hide
import fergaral.autoshorten.util.show
import kotlinx.android.synthetic.main.activity_domains_to_shorten.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by Fer on 10/02/2018.
 */
class DomainsToShortenActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DomainsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_domains_to_shorten)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DomainsViewModel::class.java)
        val adapter = DomainsAdapter(listOf(), { removeItem(it) })
        domainsRv.adapter = adapter
        domainsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel.getDomains().observe(this, Observer<List<Domain>> {
            if (it == null || it.count() <= 0)
                empty_view.show()
            else
                empty_view.hide()
            adapter.setItems(it)
        })

        addDomainFab.setOnClickListener { fabClick() }
    }

    private fun fabClick() {
        val intent = Intent(this, DomainActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                addDomainFab, getString(R.string.transition_shorten_url))
        startActivity(intent, options.toBundle())
    }

    private fun removeItem(domain: Domain) {
        doAsync {
            viewModel.deleteDomain(domain)
        }
    }
}