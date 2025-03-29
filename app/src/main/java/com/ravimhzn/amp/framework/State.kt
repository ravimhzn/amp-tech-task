package com.ravimhzn.amp.framework

import java.io.Serializable

sealed class State {
    data object Empty : State()
    data object Loading : State()
    class Loaded(val data: Serializable) : State()
    class Error(
        val error: NetworkError
    ) : State() {
        fun error() = error.error
        fun errorDescription() = error.error_description
    }
}