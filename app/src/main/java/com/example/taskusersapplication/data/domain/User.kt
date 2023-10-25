package com.example.taskusersapplication.data.domain

data class User(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var age: Int,
    var email: String,
    var domain: String,
    var phone: String,
    var birthDate: String,
    var image: String?
)
