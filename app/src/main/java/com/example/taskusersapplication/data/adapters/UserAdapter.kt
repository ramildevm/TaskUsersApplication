package com.example.taskusersapplication.data.adapters

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.taskusersapplication.data.domain.User
import com.example.taskusersapplication.data.domain.UserValidationHelper
import com.example.taskusersapplication.data.remote.UsersPagingSource
import com.example.taskusersapplication.data.remote.api.UsersApi
import javax.inject.Inject

class UserAdapter @Inject constructor(
    pager: Pager<Int, User>,
    private val api: UsersApi
) {
    val pagingFlow = pager.flow
    fun findUser(name:String) = Pager(
                PagingConfig(pageSize = 10)
            ) {
                UsersPagingSource(api, name)
            }.flow
    suspend fun createUser(user:User): ResponseResult {
        return if(UserValidationHelper.validateUser(user)) {
            val result = api.postUser(user)
            if (result==null) ResponseResult.FAILED else ResponseResult.SUCCESS
        } else
            ResponseResult.VALIDATION_ERROR
    }
    suspend fun deleteUser(user: User):ResponseResult{
        return if (api.deleteUser(user.id) == null) ResponseResult.FAILED else ResponseResult.SUCCESS
    }
}
enum class ResponseResult{
    SUCCESS,
    FAILED,
    VALIDATION_ERROR
}