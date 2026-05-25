package com.nikola0055.kmp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikola0055.kmp.database.CatatanDao
import com.nikola0055.kmp.formatDateTime
import com.nikola0055.kmp.model.Catatan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: CatatanDao) : ViewModel() {

    private val formatter = formatDateTime()

    fun insert(judul: String, isi: String) {
        val catatan = Catatan(
            tanggal = formatter,
            judul   = judul,
            catatan = isi
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(catatan)
        }
    }

    fun getCatatan(id: Long): Catatan? {
        return null
    }
}