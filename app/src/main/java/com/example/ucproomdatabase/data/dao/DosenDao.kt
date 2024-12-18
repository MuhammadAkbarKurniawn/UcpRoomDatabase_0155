package com.example.ucproomdatabase.data.dao

import androidx.room.Insert
import androidx.room.Query
import com.example.ucproomdatabase.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface DosenDao {
    @Insert
    suspend fun insertDosen(dosen: Dosen)

    @Query("SELECT * From dosen ORDER BY nama ASC")
    fun getAllDosen(): Flow<List<Dosen>>
}