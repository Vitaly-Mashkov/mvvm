package com.mashkov.mvvm.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashkov.mvvm.models.User
import com.mashkov.mvvm.network.apis.GlobalApi
import com.mashkov.mvvm.util.UiEvent
import kotlinx.coroutines.launch

class UserInfoFVM(private val appApi: GlobalApi) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _error = MutableLiveData<UiEvent>()
    val error: LiveData<UiEvent>
        get() = _error

    fun getUser(login: String) {
        viewModelScope.launch {
            try {
                _user.postValue(appApi.github.getUser(login))
            } catch (e: Exception) {
                _error.postValue(UiEvent())
            }
        }
    }

}