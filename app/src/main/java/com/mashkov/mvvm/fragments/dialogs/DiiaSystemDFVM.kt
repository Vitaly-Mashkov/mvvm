package com.mashkov.mvvm.fragments.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashkov.mvvm.util.UiDataEvent

class DiiaSystemDFVM : ViewModel() {

    private val _action = MutableLiveData<UiDataEvent<Action>>()
    val action: LiveData<UiDataEvent<Action>>
        get() = _action

    fun positive(){
        _action.value = UiDataEvent(Action.POSITIVE)
    }

    fun negative(){
        _action.value = UiDataEvent(Action.NEGATIVE)
    }

    enum class Action {
        POSITIVE, NEGATIVE
    }
}
