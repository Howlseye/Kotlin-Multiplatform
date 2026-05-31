package com.nikola0055.kmp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikola0055.kmp.network.HewanApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch {
            try {
                val result = HewanApi.service.getHewan()
                println("MainViewModel - Success: $result")
            } catch (e: Exception) {
                println("MainViewModel - Failure: ${e.message}")
            }
        }
    }
}