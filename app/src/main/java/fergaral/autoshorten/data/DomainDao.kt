package fergaral.autoshorten.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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