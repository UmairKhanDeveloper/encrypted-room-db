package com.example.notesapp.presentation.screens.home

import EncryptionHelper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.data.database.NoteDataBase
import com.example.notesapp.data.entity.Note
import com.example.notesapp.domain.repository.Repository
import com.example.notesapp.presentation.navigation.screens
import com.example.notesapp.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {

    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val noteDataBase = remember { NoteDataBase.getDataBase(context) }
    val repository = remember { Repository(noteDataBase) }
    val viewModel = remember { MainViewModel(repository) }

    val allNoteData by viewModel.allNotes.observeAsState(emptyList())

    Scaffold(
        containerColor = Color(0xFF1E1E1E),
        topBar = {
            HomeTopBar()
        },
        floatingActionButton = {
            AddNoteFabUI {
                navController.navigate(screens.Detail.route)
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(allNoteData) { note ->
                NoteItem(note = note)
            }
        }
    }
}


@Composable
fun NoteItem(note: Note) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2A2A2A))
            .padding(16.dp)
    ) {

        Text(
            text = EncryptionHelper.decrypt(note.title),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = EncryptionHelper.decrypt(note.des),
            fontSize = 14.sp,
            color = Color(0xFFBDBDBD),
            maxLines = 3
        )
    }

    Log.e("NOTE_DEBUG", "Encrypted: ${note.title}")
    Log.e("NOTE_DEBUG", "Decrypted: ${EncryptionHelper.decrypt(note.title)}")
    Log.e("NOTE_DEBUG", "Encrypted: ${note.des}")
    Log.e("NOTE_DEBUG", "Decrypted: ${EncryptionHelper.decrypt(note.des)}")
}


@Composable
fun AddNoteFabUI(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color(0xFF2A2A2A),
        shape = CircleShape,
        modifier = Modifier.padding(bottom = 100.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopBar() {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Notes",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    CenterAlignedTopAppBar(
        title = {

                Text(
                    text = "Notes",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(end = 150.dp)
                )

        },
        actions = {

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1E1E1E)
        )
    )
}