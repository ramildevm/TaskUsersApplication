package com.example.taskusersapplication.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.taskusersapplication.data.domain.User
import com.example.taskusersapplication.data.remote.api.UsersApi
import retrofit2.HttpException

class UsersPagingSource (
    private val api: UsersApi,
    private val name: String = ""
) : PagingSource<Int, User>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, User> {
        try {
            val nextPageNumber = params.key ?: 0
            val response = if(name.isEmpty())
                api.getUsers(nextPageNumber, 10)
            else
                api.getUsersByName(nextPageNumber, 10, name)
            return LoadResult.Page(
                data = response.users,
                prevKey = null,
                nextKey = if (response.users.isNotEmpty()) response.skip + 10 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(10) ?: anchorPage?.nextKey?.minus(10)
        }
    }
}
