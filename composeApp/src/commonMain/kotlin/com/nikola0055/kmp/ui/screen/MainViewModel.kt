package com.nikola0055.kmp.ui.screen

import androidx.lifecycle.ViewModel
import com.nikola0055.kmp.model.Mahasiswa

class MainViewModel: ViewModel() {
    val data = listOf(
        Mahasiswa(id = 1, 607062400055, "Nikola Glenasius Shinichi Sagala", "D3IF-04-48"),
        Mahasiswa(id = 2, 607062400001, "Aditya Pratama", "D3IF-01-48"),
        Mahasiswa(id = 3, 607062400002, "Bella Clarissa", "D3IF-02-48"),
        Mahasiswa(id = 4, 607062400003, "Candra Wijaya", "D3IF-03-48"),
        Mahasiswa(id = 5, 607062400004, "Dina Fitriani", "D3IF-04-48"),
        Mahasiswa(id = 6, 607062400005, "Eko Saputra", "D3IF-01-48"),
        Mahasiswa(id = 7, 607062400006, "Fanya Maharani", "D3IF-02-48"),
        Mahasiswa(id = 8, 607062400007, "Gilang Ramadhan", "D3IF-03-48"),
        Mahasiswa(id = 9, 607062400008, "Hana Pertiwi", "D3IF-04-48"),
        Mahasiswa(id = 10, 607062400009, "Indra Lesmana", "D3IF-01-48"),
        Mahasiswa(id = 11, 607062400010, "Jasmine Putri", "D3IF-02-48"),
        Mahasiswa(id = 12, 607062400025, "Kevin Sanjaya", "D3IF-03-48"),
        Mahasiswa(id = 13, 607062400042, "Laras Ati", "D3IF-04-48"),
        Mahasiswa(id = 14, 607062400050, "Muhammad Arifin", "D3IF-01-48"),
        Mahasiswa(id = 15, 607062400075, "Nadia Utami", "D3IF-02-48"),
        Mahasiswa(id = 16, 607062400088, "Oky Setiawan", "D3IF-03-48"),
        Mahasiswa(id = 17, 607062400100, "Putri Rahayu", "D3IF-04-48"),
        Mahasiswa(id = 18, 607062400115, "Rian Hidayat", "D3IF-01-48"),
        Mahasiswa(id = 19, 607062400125, "Siska Amelia", "D3IF-02-48"),
        Mahasiswa(id = 20, 607062400140, "Taufik Ismail", "D3IF-03-48"),
        Mahasiswa(id = 21, 607062400150, "Vina Panduwinata", "D3IF-04-48")
    )

    fun getMahasiswa(id: Long): Mahasiswa? {
        return data.find { it.id == id }
    }
}