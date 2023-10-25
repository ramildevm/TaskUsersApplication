package com.example.taskusersapplication.ui.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskusersapplication.ui.theme.BackgroundColor
import com.example.taskusersapplication.ui.presentation.UsersListContent
import com.example.taskusersapplication.ui.viewmodels.UsersViewModel

@Composable
fun HomeNavGraph(navController: NavHostController) {
    val usersViewModel = hiltViewModel<UsersViewModel>()
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeScreen.UsersList.route
    ) {
        composable(route = HomeScreen.UsersList.route) {
            UsersListContent(navController, usersViewModel)
        }
        homeDetailsNavGraph(navController = navController, usersViewModel)
    }
}

sealed class HomeScreen(val route: String) {
    object UsersList : HomeScreen(route = "USERS")
}