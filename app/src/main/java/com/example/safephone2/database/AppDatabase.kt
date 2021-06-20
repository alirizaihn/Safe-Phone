package com.example.safephone2.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ProfileModel::class],version = 9)
@TypeConverters(AppsTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notedao(): NoteDao

}