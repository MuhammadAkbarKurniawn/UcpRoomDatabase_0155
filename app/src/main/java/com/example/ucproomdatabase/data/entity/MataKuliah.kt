package com.example.ucproomdatabase.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MataKuliah")
data class MataKuliah(
    @PrimaryKey
    val kode : String,
    val nama : String,
    val SKS : String,
    val semester : String,
    val jenis : String,
    val dosenPengampu : String,
)
