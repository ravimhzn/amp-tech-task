package com.ravimhzn.amp.framework

import java.io.Serializable

sealed class State {
    data object Empty : State()
    data object Loading : State()
    class Loaded(val data: Serializable) : State()
    class Error(
        val error: NetworkError
    ) : State() {
        fun errorCode() = error.statusCode
        fun errorMessage() = error.statusMessage
    }
}