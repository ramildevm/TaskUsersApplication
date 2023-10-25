package com.example.taskusersapplication.ui.presentation.details

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.taskusersapplication.data.adapters.ResponseResult
import com.example.taskusersapplication.data.domain.User
import com.example.taskusersapplication.data.domain.UserValidationHelper
import com.example.taskusersapplication.ui.theme.TaskUsersApplicationTheme
import com.example.taskusersapplication.ui.viewmodels.UsersViewModel
import com.google.gson.Gson

@Composable
fun UserAddContent(navController: NavHostController, usersViewModel: UsersViewModel) {
    val context = LocalContext.current
    val user = remember {
        mutableStateOf(
            User(
                0,
                "",
                "",
                0,
                "",
                "",
                "",
                "",
                null
            )
        )
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    Box(Modifier.background(Color.White)) {
        Card(
            modifier = Modifier
                .padding(top = 5.dp)
                .padding(10.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, Color.LightGray)
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier.verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val modifier = Modifier.padding(horizontal = 10.dp)
                Spacer(Modifier.height(8.dp))
                Row(modifier) {
                    if (user.value.image != null)
                        AsyncImage(
                            model = user.value.image,
                            contentDescription = user.value.firstName,
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
                    }
                }
                AddCard(modifier, "First name: ", user.value.firstName) {
                    user.value.firstName = it
                }
                AddCard(modifier, "Last name: ", user.value.lastName) {
                    user.value.lastName = it
                }
                AddCard(modifier, "Age: ", user.value.age.toString(), isNumber = true) {
                    try {
                        user.value.age = it.toInt()
                    } catch (e: NumberFormatException) {
                        e.stackTrace
                    }
                }
                AddCard(modifier, "Birth date: ", user.value.birthDate) {
                    user.value.birthDate = it
                }
                AddCard(modifier, "Phone: ", user.value.phone) {
                    user.value.phone = it
                }
                AddCard(modifier, "Email: ", user.value.email) {
                    user.value.email = it
                }
                AddCard(modifier, "Website: ", user.value.domain) {
                    user.value.domain = it
                }
                Spacer(Modifier.height(100.dp))
            }

        }
        Text(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 5.dp)
                .align(Alignment.TopCenter),
            text = "Add new user",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary
        )
        Button(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .wrapContentSize()
                .padding(horizontal = 50.dp, vertical = 20.dp),
            onClick = {
                navController.popBackStack()
            }) {
            Text(
                text = "Cancel",
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.h6
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .wrapContentSize()
                .padding(horizontal = 50.dp, vertical = 20.dp),
            onClick = {
                usersViewModel.createUser(user.value)
                usersViewModel.insertResponseResult.observe(lifecycleOwner){result ->
                    when(result){
                        ResponseResult.SUCCESS -> {
                            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                        ResponseResult.FAILED -> Toast.makeText(context, "Internal server error", Toast.LENGTH_SHORT).show()
                        ResponseResult.VALIDATION_ERROR -> Toast.makeText(context, "Invalid data", Toast.LENGTH_SHORT).show()
                        null -> {}
                    }
                }
            }) {
            Text(
                text = "Save",
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.h6
            )
        }
    }
}


@Composable
fun AddCard(
    modifier: Modifier,
    label: String,
    value: String,
    isNumber: Boolean = false,
    onChange: (String) -> Unit
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.h5,
        )
        var text by remember {
            mutableStateOf(value)
        }
        var isError by remember {
            mutableStateOf(false)
        }
        OutlinedTextField(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .weight(1f),
            value = text,
            isError = isError,
            textStyle = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.secondary),
            onValueChange = {
                text = if(!isNumber)
                    it
                else{
                    if(UserValidationHelper.validate(it, true))
                        it
                    else
                        ""
                }
                isError = if (UserValidationHelper.validate(it, isNumber)) {
                    onChange(it)
                    false
                } else
                    true
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = if (isNumber) KeyboardType.Decimal else KeyboardType.Text)
        )

    }
}