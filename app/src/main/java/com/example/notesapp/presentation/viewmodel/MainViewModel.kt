package com.example.notesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.entity.Note
import com.example.notesapp.domain.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val allNotes = repository.getAllNotes()


    fun insertNote(title: String, description: String) {

        val encryptedTitle = EncryptionHelper.encrypt(title)
        val encryptedDesc = EncryptionHelper.encrypt(description)

        val note = Note(
            id = null,
           title = encryptedTitle,
            des = encryptedDesc
        )

        viewModelScope.launch {
            repository.insertNote(note)
        }
    }    fun updateNote(note: Note) = viewModelScope.launch { repository.updateNote(note) }


}