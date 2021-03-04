package com.mashkov.mvvm.util

import java.io.Serializable

open class UiEvent {
    var hasBeenHandled = false
        protected set
}

open class UiDataEvent<out T>(private val content: T) : UiEvent(), Serializable {

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
