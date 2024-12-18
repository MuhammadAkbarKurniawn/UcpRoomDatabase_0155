package com.example.ucproomdatabase.repository

import com.example.ucproomdatabase.data.dao.MatkulDao
import com.example.ucproomdatabase.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepoMatkul
    (private val matkulDao: MatkulDao) : RepositoryMatkul {

        override suspend fun insertMatkul(mataKuliah: MataKuliah) {
            matkulDao.insertMataKuliah(mataKuliah)
        }

        override fun getAllMatkul(): Flow<List<MataKuliah>> {
            return matkulDao.getAllMataKuliah()
        }

        override fun getMatkul(kode: String): Flow<MataKuliah> {
            return matkulDao.getMataKuliah(kode)
        }

        override suspend fun deleteMatkul(mataKuliah: MataKuliah) {
            matkulDao.deleteMataKuliah(mataKuliah)
        }

        override suspend fun updateMatkul(mataKuliah: MataKuliah) {
            matkulDao.updateMataKuliah(mataKuliah)
        }
}