package com.xcelk.guessinggame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultViewModel(finalResult: String): ViewModel() {
    val result = finalResult
}
