package com.example.safephone2.database

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM profile_table")
    fun  getAllNotes(): List<ProfileModel>

    @Query("SELECT * FROM profile_table WHERE isActive LIKE :isAct")
    fun getActiveNotes(isAct: Boolean= true): List<ProfileModel>

    @Insert
    fun insertAll(vararg note: ProfileModel)

    @Delete
    fun deleteNote(vararg note: ProfileModel)

    @Update
    fun updateNote(vararg note: ProfileModel)

}