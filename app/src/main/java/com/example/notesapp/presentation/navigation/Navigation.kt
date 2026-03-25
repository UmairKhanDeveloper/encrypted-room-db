package com.example.notesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notesapp.presentation.screens.detail.Detail
import com.example.notesapp.presentation.screens.home.Home

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = screens.Home.route) {
        composable(screens.Home.route) { Home(navController) }
        composable(screens.Detail.route) { Detail(navController) }
    }

}

sealed class screens(val route: String, val title: String) {
    object Home : screens("Home", "Home")
    object Detail : screens("Detail", "Detail")
}