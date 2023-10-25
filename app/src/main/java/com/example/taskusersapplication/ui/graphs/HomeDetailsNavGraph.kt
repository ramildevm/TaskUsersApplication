package com.example.taskusersapplication.ui.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.taskusersapplication.ui.presentation.details.UserAddContent
import com.example.taskusersapplication.ui.presentation.details.UserInfoContent
import com.example.taskusersapplication.ui.viewmodels.UsersViewModel

fun NavGraphBuilder.homeDetailsNavGraph(
    navController: NavHostController,
    usersViewModel: UsersViewModel
) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            val selectedUser = usersViewModel.selectedUser
            UserInfoContent(navController, selectedUser)
        }
        composable(route = DetailsScreen.AddUser.route) {
            UserAddContent(navController, usersViewModel)
//            navController.popBackStack(
//                route = DetailsScreen.Information.route,
//                inclusive = false
//            )
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object AddUser : DetailsScreen(route = "ADDUSER")
}