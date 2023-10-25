package com.example.taskusersapplication.ui.presentation.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.taskusersapplication.data.domain.User
import com.example.taskusersapplication.ui.theme.TaskUsersApplicationTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun UserInfoContent(navController: NavHostController, selectedUser: User?) {
    selectedUser?.let{ user ->
        Box(Modifier.background(Color.White)){
            Card(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .padding(10.dp)
                    .fillMaxSize(),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, Color.LightGray)
            ){
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val modifier = Modifier.padding(horizontal = 10.dp)
                    Spacer(Modifier.height(8.dp))
                    Row(modifier){
                        if(user.image!=null)
                            AsyncImage(
                                model = user.image,
                                contentDescription = user.firstName,
                                modifier = Modifier
                                    .weight(3f)
                                    .height(150.dp)
                                    .padding(5.dp)
                            )
                        Column(
                            Modifier
                                .weight(4f)
                                .padding(top = 5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            InfoCard(Modifier,"Age:", user.age.toString())
                            InfoCard(Modifier,"Birth date:     ", user.birthDate)
                        }
                    }
                    InfoCard(modifier,"Phone:", user.phone)
                    InfoCard(modifier,"Email:", user.email)
                    InfoCard(modifier,"Website:", user.domain)
                }

            }
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 50.dp, vertical = 20.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    navController.popBackStack()
                }) {
                Text(
                    text = "Go back",
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.h6
                )
            }
            Text(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 5.dp)
                    .align(Alignment.TopCenter),
                text = "${user.firstName} ${user.lastName}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun InfoCard(modifier: Modifier, label: String, text: String) {
    Row(modifier){
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.h5,
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = text,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary,
            softWrap = true
        )

    }
}

@Preview
@Composable
fun UserInfoPreview() {
    TaskUsersApplicationTheme() {
        val context = LocalContext.current
        UserInfoContent(
            navController = NavHostController(context = context),
            User(
                id = 1,
                firstName = "Ivan",
                lastName = "Ivanov",
                age = 18,
                email = "email.example@mail.com",
                birthDate = "01.01.2023",
                phone = "88005553535",
                image = "https://robohash.org/voluptatemsintnulla.png",
                domain = "http://github.com/ramildevm"
            )
        )
    }
}