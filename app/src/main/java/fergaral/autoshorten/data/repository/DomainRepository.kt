package fergaral.autoshorten.data.repository

import android.arch.lifecycle.LiveData
import fergaral.autoshorten.data.Domain

/**
 * Created by Fer on 10/02/2018.
 */
interface DomainRepository {
    fun selectAll(): LiveData<List<Domain>>
    fun insert(domain: Domain)
    fun delete(domain: Domain)
    fun exists(domainUrl: String): Boolean
    fun hasDomains(): Boolean
}