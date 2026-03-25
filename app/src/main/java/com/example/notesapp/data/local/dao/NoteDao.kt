package com.example.notesapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.data.entity.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(note: Note)

    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    suspend fun Update(note: Note)

    @Delete
    suspend fun Delete(note: Note)

}