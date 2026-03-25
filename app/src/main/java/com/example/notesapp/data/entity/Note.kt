package com.example.notesapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,

    @ColumnInfo("title")
    val title:String,

    @ColumnInfo("des")
    val des:String,
)