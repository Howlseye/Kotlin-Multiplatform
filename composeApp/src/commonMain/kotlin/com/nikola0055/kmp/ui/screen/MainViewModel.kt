package com.nikola0055.kmp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikola0055.kmp.database.CatatanDao
import com.nikola0055.kmp.model.Catatan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: CatatanDao): ViewModel() {
    val data: StateFlow<List<Catatan>> = dao.getCatatan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun getCatatan(id: Long): Catatan? {
        return data.value.find { it.id == id }
    }
}