package fergaral.autoshorten.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Fer on 08/02/2018.
 */
@Entity(indices = [(Index(value = arrayOf("url"), unique = true))])
data class Domain(@PrimaryKey val id: Int? = null, @ColumnInfo(name = "url") val url: String)