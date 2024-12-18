package com.example.ucproomdatabase.dependenciesinjection

import android.content.Context
import com.example.ucproomdatabase.data.database.AppDatabase
import com.example.ucproomdatabase.repository.LocalRepoDosen
import com.example.ucproomdatabase.repository.LocalRepoMatkul
import com.example.ucproomdatabase.repository.RepositoryDosen
import com.example.ucproomdatabase.repository.RepositoryMatkul

interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
    val repositoryMatkul: RepositoryMatkul
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {

    // Repository untuk dosen
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepoDosen(AppDatabase.getDatabase(context).DosenDao())
    }

    // Repository untuk mata kuliah
    override val repositoryMatkul: RepositoryMatkul by lazy {
        LocalRepoMatkul(AppDatabase.getDatabase(context).MatkulDao())
    }
}