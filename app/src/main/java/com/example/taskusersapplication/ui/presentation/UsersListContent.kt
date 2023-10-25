package com.example.taskusersapplication.ui.presentation

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.paging.compose.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.taskusersapplication.data.adapters.ResponseResult
import com.example.taskusersapplication.ui.graphs.DetailsScreen
import com.example.taskusersapplication.ui.viewmodels.UsersViewModel

@Composable
fun UsersListContent(navController: NavHostController, usersViewModel: UsersViewModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    Scaffold(floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                onClick = {
                    navController.navigate(route = DetailsScreen.AddUser.route)
                }
            ) {
                Icon(Icons.Rounded.Add, contentDescription = null, tint = Color.White)
            }
        }) {
        val context = LocalContext.current
        val users = usersViewModel.usersPagingFlow.collectAsLazyPagingItems()
        LaunchedEffect(users.loadState) {
            if (users.loadState.refresh is LoadState.Error) {
                Toast.makeText(
                    context,
                    "Error: " + (users.loadState.refresh as LoadState.Error).error.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            if (users.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(users) {
                        Box(Modifier
                            .fillMaxWidth()
                            .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = EaseInOut
                            )
                        )){
                            var userObject by remember {
                                mutableStateOf(it)
                            }
                            userObject?.let { user->
                                UserItem(
                                    user = user,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 10.dp, horizontal = 5.dp)
                                        .clickable {
                                            usersViewModel.selectedUser = user
                                            navController.navigate(DetailsScreen.Information.route)
                                        }
                                ) {
                                    usersViewModel.deleteUser(user)
                                    usersViewModel.deleteResponseResult.observe(lifecycleOwner) { result ->
                                        if (result == ResponseResult.SUCCESS) {
                                            userObject = null //Delete imitation
                                        }
                                        else
                                            Toast.makeText(context, "Deleting error", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                    item {
                        if (users.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                        else{
                            Spacer(modifier = Modifier.height(70.dp))
                        }
                    }

                }
            }
        }
    }
}