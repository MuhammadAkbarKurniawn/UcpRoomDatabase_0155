package com.example.ucproomdatabase.repository

import com.example.ucproomdatabase.data.dao.DosenDao
import com.example.ucproomdatabase.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepoDosen
    (private val dosenDao: DosenDao) : RepositoryDosen {

    override suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    override fun getAllDosen(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

}
