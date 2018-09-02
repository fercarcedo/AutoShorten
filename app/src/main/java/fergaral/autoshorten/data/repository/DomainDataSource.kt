package fergaral.autoshorten.data.repository

import androidx.lifecycle.LiveData
import android.database.sqlite.SQLiteConstraintException
import fergaral.autoshorten.data.Domain
import fergaral.autoshorten.data.DomainDao
import fergaral.autoshorten.data.DomainExistsException
import javax.inject.Inject

/**
 * Created by Fer on 10/02/2018.
 */
class DomainDataSource @Inject constructor(private val dao: DomainDao): DomainRepository {

    override fun delete(domain: Domain) = dao.delete(domain)

    override fun insert(domain: Domain) {
        try {
            dao.insert(domain)
        } catch (e: SQLiteConstraintException) {
            throw DomainExistsException()
        }
    }

    override fun selectAll(): LiveData<List<Domain>> = dao.selectAll()

    override fun exists(domainUrl: String): Boolean = dao.exists(domainUrl)

    override fun hasDomains(): Boolean = dao.hasDomains()
}