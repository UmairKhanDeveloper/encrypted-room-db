package com.example.notesapp.domain.repository

import com.example.notesapp.data.database.NoteDataBase
import com.example.notesapp.data.entity.Note

class Repository(private val db: NoteDataBase) {

    fun getAllNotes() = db.getNoteDao().getAllNotes()

    suspend fun insertNote(note: Note) {
        db.getNoteDao().Insert(note)
    }

    suspend fun updateNote(note: Note) {
        db.getNoteDao().Update(note)
    }

    suspend fun deleteNote(note: Note) {
        db.getNoteDao().Delete(note)
    }


}