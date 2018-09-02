package fergaral.autoshorten.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Fer on 08/02/2018.
 */
@Database(entities = [(Domain::class)], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun domainDao(): DomainDao
}