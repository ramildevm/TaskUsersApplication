package com.example.taskusersapplication.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.taskusersapplication.data.domain.User
import com.example.taskusersapplication.ui.theme.TaskUsersApplicationTheme
import com.example.taskusersapplication.ui.viewmodels.UsersViewModel
import kotlin.random.Random

@Composable
fun UserItem(
    user: User,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
){
    Card(
        modifier = modifier,
        elevation = 4.dp
    ){
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(16.dp)
        ){
            LeadingImage(user)
            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ){
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                )
                Text(
                    text = user.email,
                    fontSize = 16.sp
                )
            }
            IconButton(onClick = {
                onDelete()
            }){
                Icon(Icons.Rounded.Delete, null)
            }
        }
    }
}

@Composable
fun LeadingImage(user: User) {
    val randomColor = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    Box(
        Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(1000.dp))
    ){
        user.image?.let {
            AsyncImage(
                model = user.image,
                contentDescription = user.firstName,
                modifier = Modifier
                    .fillMaxSize()
            )
        } ?: Box(modifier = Modifier
            .fillMaxSize()
            .background(randomColor)){
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = user.firstName[0].toString().uppercase(),
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun UserItemPreview() {
    TaskUsersApplicationTheme {
        UserItem(
            user = User(
                id = 1,
                firstName = "Ivan",
                lastName = "Ivanov",
                age = 18,
                email = "email.example@mail.com",
                birthDate = "01.01.2023",
                phone = "88005553535",
                image = null,
                domain = "http://github.com/ramildevm"
            ),
            modifier = Modifier.fillMaxWidth()
        ) {

        }
    }
}