package com.nikola0055.kmp.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikola0055.kmp.model.Agent
import com.nikola0055.kmp.network.AgentApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Agent>())
        private set

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch {
            try {
                data.value = AgentApi.service.getAgent()
            } catch (e: Exception) {
                println("MainViewModel - Failure: ${e.message}")
            }
        }
    }
}