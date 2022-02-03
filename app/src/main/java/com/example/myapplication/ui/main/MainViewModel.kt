package com.example.myapplication.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class MainViewModel : ViewModel() {

    init {
        viewModelScope
    }

    lateinit var number: String

    override fun onCleared() {
        Log.d("MainFragment", "$number onCleared ViewModel")
        super.onCleared()
    }
}