package com.example.ucproomdatabase.repository

import com.example.ucproomdatabase.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMatkul {

    suspend fun insertMatkul(mataKuliah: MataKuliah)

    fun getAllMatkul(): Flow<List<MataKuliah>>

    fun getMatkul(kode: String): Flow<MataKuliah>

    suspend fun deleteMatkul(mataKuliah: MataKuliah)

    suspend fun updateMatkul(mataKuliah: MataKuliah)
}
