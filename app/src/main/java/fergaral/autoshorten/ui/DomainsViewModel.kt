package fergaral.autoshorten.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import fergaral.autoshorten.data.Domain
import fergaral.autoshorten.data.repository.DomainRepository
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by Fer on 10/02/2018.
 */
class DomainsViewModel @Inject constructor(private val repository: DomainRepository) : ViewModel() {
    private var domains: LiveData<List<Domain>> = repository.selectAll()

    fun getDomains(): LiveData<List<Domain>> = domains

    fun insertDomain(domainUrl: String) {
        repository.insert(Domain(null, domainUrl))
    }

    fun deleteDomain(domain: Domain) {
        repository.delete(domain)
    }
}