package com.example.safephone2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "profile_table")
 data class ProfileModel (
   @ColumnInfo(name = "title")
   var title:String,
   @ColumnInfo(name = "endTime")
   var endTime:String,
   @ColumnInfo(name = "isActive")
   var isActive:Boolean,
   @ColumnInfo(name = "apps")
   var apss:ArrayList<String>,
   @PrimaryKey(autoGenerate = true)
   var noteId: Int = 0)


class AppsTypeConverter {

  @TypeConverter
  fun fromString(value: String?): ArrayList<String> {
    val listType = object : TypeToken<ArrayList<String>>() {}.type
    return Gson().fromJson(value, listType)
  }

  @TypeConverter
  fun frmArrayList(list: ArrayList<String?>): String {
    return Gson().toJson(list)
  }
}