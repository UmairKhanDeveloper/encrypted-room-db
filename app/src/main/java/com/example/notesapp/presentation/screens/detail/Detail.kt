package com.example.notesapp.presentation.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.R
import com.example.notesapp.data.database.NoteDataBase
import com.example.notesapp.domain.repository.Repository
import com.example.notesapp.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Detail(navController: NavController) {
    val context = LocalContext.current
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var showSaveDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val noteDataBase = remember { NoteDataBase.getDataBase(context) }
    val repository = remember { Repository(noteDataBase) }
    val viewModel = remember { MainViewModel(repository) }

    Scaffold(containerColor = Color(0xFF1E1E1E), topBar = {
        DetailTopBar(
            onBackClick = { navController.popBackStack() },
            onSaveClick = { showSaveDialog = true }

        )
    }) { paddingValues ->

        DetailContent(
            modifier = Modifier.padding(paddingValues),
            title = title,
            content = description,
            onTitleChange = { title = it },
            onContentChange = { description = it },

            )
    }

    if (showSaveDialog) {
        SaveChangesDialog(
            onDiscard = {
                showSaveDialog = false
            },
            onSave = {
                scope.launch {
                    viewModel.insertNote(
                       title = title,
                        description = description
                    )
                    showSaveDialog=false
                    navController.popBackStack()

                }
            }
        )
    }
}


@Composable
fun SaveChangesDialog(onDiscard: () -> Unit, onSave: () -> Unit) {
    androidx.compose.ui.window.Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF2A2A2A))
                .padding(20.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.info_outline),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Save changes?",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DialogButton(
                        text = "Discard",
                        color = Color.Red,
                        modifier = Modifier.weight(1f),
                        onClick = onDiscard
                    )
                    DialogButton(
                        text = "Save",
                        color = Color(0xFF2ECC71),
                        modifier = Modifier.weight(1f),
                        onClick = onSave
                    )
                }
            }
        }
    }
}

@Composable
fun DialogButton(text: String, color: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,

    ) {
    TopAppBar(
        title = {
            DetailBarIcon(icon = R.drawable.arrow_back, onClick = onBackClick)
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(end = 12.dp)
            ) {

                DetailBarIcon(icon = R.drawable.save, onClick = onSaveClick)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1E1E1E))
    )
}


@Composable
fun DetailBarIcon(icon: Int, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF2A2A2A))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,

    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        NoteTextField(
            value = title,
            onValueChange = onTitleChange,
            placeholder = "Title",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            singleLine = true,

            )

        Spacer(modifier = Modifier.height(12.dp))

        NoteTextField(
            value = content,
            onValueChange = onContentChange,
            placeholder = "Type something...",
            fontSize = 16.sp,

            )
    }
}

@Composable
fun NoteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, fontSize = fontSize, color = Color.Gray) },
        textStyle = TextStyle(fontSize = fontSize, fontWeight = fontWeight, color = Color.White),
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White
        ),
        modifier = modifier
    )
}
