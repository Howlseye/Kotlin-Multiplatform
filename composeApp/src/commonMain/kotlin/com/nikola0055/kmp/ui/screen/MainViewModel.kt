package com.nikola0055.kmp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikola0055.kmp.database.MahasiswaDao
import com.nikola0055.kmp.model.Mahasiswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: MahasiswaDao): ViewModel() {
    val data: StateFlow<List<Mahasiswa>> = dao.getMahasiswa().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
}