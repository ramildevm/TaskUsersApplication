package com.example.taskusersapplication.data.remote.dto

import com.example.taskusersapplication.data.domain.User

data class UsersDto(
    val users:List<User>,
    val skip:Int,
    val limit:Int
)
