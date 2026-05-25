package com.nikola0055.kmp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikola0055.kmp.database.MahasiswaDao
import com.nikola0055.kmp.model.Mahasiswa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: MahasiswaDao) : ViewModel() {

    fun insert(nama: String, nim: Long, kelas: String) {
        val mahasiswa = Mahasiswa(
            nama = nama,
            nim = nim,
            kelas = kelas
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(mahasiswa)
        }
    }

    suspend fun getMahasiswa(id: Long): Mahasiswa? {
        return dao.getMahasiswaById(id)
    }

    fun update(id: Long, nama: String, nim: Long, kelas: String) {
        val mahasiswa = Mahasiswa(
            id = id,
            nama = nama,
            nim = nim,
            kelas = kelas
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(mahasiswa)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}