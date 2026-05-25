package com.nikola0055.kmp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mahasiswa")

data class Mahasiswa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nim: Long,
    val nama: String,
    val kelas: String
)
