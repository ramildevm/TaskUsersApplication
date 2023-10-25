package com.example.taskusersapplication.ui.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskusersapplication.ui.graphs.HomeNavGraph


@Composable
fun MainScreenContent(navController: NavHostController = rememberNavController()) {
    Scaffold {innerPadding->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeNavGraph(navController = navController)
        }
    }
}

