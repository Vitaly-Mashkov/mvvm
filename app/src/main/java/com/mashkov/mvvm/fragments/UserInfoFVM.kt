package com.mashkov.mvvm.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashkov.mvvm.models.User
import com.mashkov.mvvm.network.apis.GlobalApi
import kotlinx.coroutines.launch

class UserInfoFVM(private val appApi: GlobalApi) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getUser(login: String) {
        viewModelScope.launch {
            try {
                _user.postValue(appApi.github.getUser(login))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}