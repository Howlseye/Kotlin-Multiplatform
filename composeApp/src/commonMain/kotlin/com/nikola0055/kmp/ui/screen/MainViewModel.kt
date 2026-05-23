package com.nikola0055.kmp.ui.screen

import androidx.lifecycle.ViewModel
import com.nikola0055.kmp.model.Catatan

class MainViewModel: ViewModel() {
    val data = listOf(
        Catatan(
            1,
            "Kuliah Mobpro 17 Feb",
            "Kuliah hari pertama. Ternyata keren juga yang mau dipelajari di Mobpro1.",
            "2026-02-17 12:34:56"
        ),
        Catatan(
            2,
            "Kuliah Mobpro 19 Feb",
            "Praktikum pertama: running modul. Alhamdulillah hari ini lancar.",
            "2026-02-19 12:34:56"
        ),
        Catatan(
            3,
            "Kuliah Mobpro 24 Feb",
            "Udah masuk materi nih, semoga bisa dipahami.. Harus semangat!!",
            "2026-02-23 12:34:56"
        ),
        Catatan(
            4,
            "Kuliah Mobpro 26 Feb",
            "Praktikum bikin aplikasi Counter. Masih sederhana sih, yang penting jalan..",
            "2026-02-26 12:34:56"
        ),
        Catatan(
            5,
            "Kuliah Mobpro 03 Mar",
            "Bahas tentang pemakaian gambar di aplikasi Android. Membuka wawasan.",
            "2026-03-03 12:34:56"
        ),
        Catatan(
            6,
            "Kuliah Mobpro 05 Mar",
            "Praktikum kali ini bikin aplikasi Galeri Hewan.. Klik tombol lanjut, maka foto dan nama hewannya berubah.",
            "2026-03-05 12:34:56"
        )
    )
}