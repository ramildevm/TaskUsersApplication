package com.example.taskusersapplication.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.taskusersapplication.data.adapters.ResponseResult
import com.example.taskusersapplication.data.adapters.UserAdapter
import com.example.taskusersapplication.data.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val adapter: UserAdapter
) :ViewModel() {
    val usersPagingFlow = adapter.pagingFlow.cachedIn(viewModelScope)
    var selectedUser:User? = null

    val insertResponseResult = MutableLiveData<ResponseResult>()
    val deleteResponseResult = MutableLiveData<ResponseResult>()

    private var currentNameValue: String? = null

    private var currentSearchResult: Flow<PagingData<User>>? = null
    fun createUser(user: User){
        viewModelScope.launch {
            insertResponseResult.postValue(adapter.createUser(user))
        }
    }
    fun findUserByName(name: String):Flow<PagingData<User>> {
        val lastResult = currentSearchResult
        if (name == currentNameValue && lastResult != null) {
            return lastResult
        }
        currentNameValue = name
        val newResult = adapter.findUser(name)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
    fun deleteUser(user: User){
        viewModelScope.launch {
            deleteResponseResult.postValue(adapter.deleteUser(user))
        }
    }
}