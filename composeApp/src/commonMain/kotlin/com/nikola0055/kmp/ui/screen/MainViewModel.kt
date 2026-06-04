package com.nikola0055.kmp.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikola0055.kmp.model.Agent
import com.nikola0055.kmp.network.AgentApi
import com.nikola0055.kmp.network.ApiStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Agent>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    init {
        retrieveData()
    }

    fun retrieveData() {
        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            try {
                data.value = AgentApi.service.getAgent()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                println("MainViewModel - Failure: ${e.message}")
            }
        }
    }
}