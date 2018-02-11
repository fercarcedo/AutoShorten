package fergaral.autoshorten.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Fer on 08/02/2018.
 */
@Database(entities = [(Domain::class)], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun domainDao(): DomainDao
}