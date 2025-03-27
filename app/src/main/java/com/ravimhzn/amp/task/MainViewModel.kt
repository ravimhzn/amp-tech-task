package com.ravimhzn.amp.task

import com.ravimhzn.amp.framework.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    init {
        //Todo
        println("This is a viewModel")
    }
}