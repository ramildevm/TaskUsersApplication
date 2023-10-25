package com.example.taskusersapplication.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.taskusersapplication.data.domain.User
import com.example.taskusersapplication.data.remote.UsersPagingSource
import com.example.taskusersapplication.data.remote.api.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUsersApi(): UsersApi {
        return Retrofit.Builder()
            .baseUrl(UsersApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideUserPager(usersApi: UsersApi): Pager<Int, User> {
        return Pager(
            pagingSourceFactory = {UsersPagingSource(usersApi)},
            config = PagingConfig(pageSize = 10),
        )
    }
}