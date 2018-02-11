package fergaral.autoshorten.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * Created by Fer on 08/02/2018.
 */
@Dao
interface  DomainDao {
    @Query("SELECT * FROM DOMAIN")
    fun selectAll(): LiveData<List<Domain>>

    @Insert()
    fun insert(domain: Domain)

    @Delete
    fun delete(domain: Domain)

    @Query("SELECT EXISTS (SELECT * FROM DOMAIN WHERE url = :domainUrl)")
    fun exists(domainUrl: String): Boolean

    @Query("SELECT COUNT(*) FROM DOMAIN")
    fun hasDomains(): Boolean
}