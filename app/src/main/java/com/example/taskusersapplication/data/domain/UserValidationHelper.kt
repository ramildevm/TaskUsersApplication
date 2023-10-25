package com.example.taskusersapplication.data.domain

object UserValidationHelper {
    fun validate(value: String, isNumber:Boolean = false):Boolean {
        if(isNumber)
            return try {
                value.toInt()
                true
            }
            catch (e:NumberFormatException){
                false
            }
        return value.trim().isNotEmpty()
    }
    fun validateUser(user:User):Boolean {
        return validate(user.firstName) && validate(user.lastName) && validate(user.age.toString(), true) && validate(user.email) && validate(user.phone) && validate(user.birthDate) && validate(user.domain)
    }
}