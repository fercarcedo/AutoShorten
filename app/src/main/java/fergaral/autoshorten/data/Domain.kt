package fergaral.autoshorten.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Fer on 08/02/2018.
 */
@Entity(indices = [(Index(value = arrayOf("url"), unique = true))])
data class Domain(@PrimaryKey val id: Int? = null, @ColumnInfo(name = "url") val url: String)