package com.example.taskusersapplication.ui.viewmodels

import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.map
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.taskusersapplication.data.adapters.ResponseResult
import com.example.taskusersapplication.data.adapters.UserAdapter
import com.example.taskusersapplication.data.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val adapter: UserAdapter
) :ViewModel() {
    val usersPagingFlow = adapter.pagingFlow.cachedIn(viewModelScope)
    var selectedUser:User? = null

    val insertResponseResult = MutableLiveData<ResponseResult>()
    val deleteResponseResult = MutableLiveData<ResponseResult>()

    fun createUser(user: User){
        viewModelScope.launch {
            insertResponseResult.postValue(adapter.createUser(user))
        }
    }
    fun deleteUser(user: User){
        viewModelScope.launch {
            deleteResponseResult.postValue(adapter.deleteUser(user))
        }
    }
}