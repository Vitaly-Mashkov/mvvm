package com.mashkov.mvvm.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashkov.mvvm.models.User
import com.mashkov.mvvm.network.apis.GlobalApi
import kotlinx.coroutines.launch

class UserFVM(private val appApi: GlobalApi) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users


    fun getUsers() {
        viewModelScope.launch {
            try {
                _users.postValue(appApi.github.getUsers())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}