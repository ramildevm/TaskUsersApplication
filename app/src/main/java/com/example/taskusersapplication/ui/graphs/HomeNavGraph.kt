package com.example.taskusersapplication.ui.graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskusersapplication.ui.presentation.home.UsersListContent
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