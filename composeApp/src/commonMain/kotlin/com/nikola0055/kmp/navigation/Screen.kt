package com.nikola0055.kmp.navigation

const val KEY_ID_MAHASISWA = "idMahasiswa"
sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object FormBaru : Screen("detailScreen")
    object FormUbah : Screen("detailScreen/{$KEY_ID_MAHASISWA}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}