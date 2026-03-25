package com.example.notesapp.domain.usecase


import com.example.notesapp.data.entity.Note

sealed class ResultState<out T> {
    object Loading:ResultState<Note>()
    data class Succses<T>(val succses: T):ResultState<T>()
    data class Error(val error: Throwable):ResultState<Note>()
}