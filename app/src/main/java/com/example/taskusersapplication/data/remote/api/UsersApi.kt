package com.example.taskusersapplication.data.remote.api

import com.example.taskusersapplication.data.domain.User
import com.example.taskusersapplication.data.remote.dto.UsersDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApi {
    @GET("users")
    suspend fun getUsers(
        @Query("skip") page: Int,
        @Query("limit") pageCount: Int
    ): UsersDto

    @GET("users/search")
    suspend fun getUsersByName(
        @Query("skip") page: Int,
        @Query("limit") pageCount: Int,
        @Query("q") name: String): UsersDto

    @POST("users/add")
    suspend fun postUser(@Body user: User) : User?

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id:Int) : User?

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}