package com.jeonhui.mydaily.structures

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.TypeConverter
import androidx.room.Update
import java.io.Serializable
import java.sql.Date


@Entity(tableName = "Diary")
data class Diary(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "emotion") var emotion: String,
    @ColumnInfo(name = "createdAt") var createAt: Long,
    @ColumnInfo(name = "content") var content: String
) : Serializable {
    constructor() : this(0, "", "", 0, "")

    fun updateDiary(id: Int?, title: String?, emotion: String?, createAt: Long?, content: String?): Diary =
        Diary(
            id ?: this.id,
            title ?: this.title,
            emotion ?: this.emotion,
            createAt ?: this.createAt,
            content ?: this.content,
        )
}

@Dao
interface DiaryDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insert(diary: Diary)

    @Update
    suspend fun update(diary: Diary)

    @Delete
    suspend fun delete(diary: Diary)

    @Query("select * from Diary")
    suspend fun getAllDiary(): List<Diary>
}

class DiaryTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? = date?.time
}