package com.example.ucproomdatabase.repository

import com.example.ucproomdatabase.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {

    suspend fun insertDosen(dosen: Dosen)

    fun getAllDosen () : Flow<List<Dosen>>
}