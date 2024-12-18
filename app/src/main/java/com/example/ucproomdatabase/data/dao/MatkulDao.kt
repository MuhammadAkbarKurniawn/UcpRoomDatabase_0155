package com.example.ucproomdatabase.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucproomdatabase.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface MatkulDao {
    @Insert
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    @Query("SELECT * From matakuliah ORDER BY nama ASC")
    fun getAllMataKuliah(): Flow<List<MataKuliah>>

    @Query("SELECT * From matakuliah WHERE kode = :kode")
    fun getMataKuliah(kode: String): Flow<MataKuliah>

    @Delete
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    @Update
    suspend fun updateMataKuliah (mataKuliah: MataKuliah)
}