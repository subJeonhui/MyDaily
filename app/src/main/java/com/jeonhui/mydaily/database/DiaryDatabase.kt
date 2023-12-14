package com.jeonhui.mydaily.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jeonhui.mydaily.structures.Diary
import com.jeonhui.mydaily.structures.DiaryDAO
import com.jeonhui.mydaily.structures.DiaryTypeConverter

@Database(entities = [Diary::class], version = 1)
@TypeConverters(DiaryTypeConverter::class)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDAO
}