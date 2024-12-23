package com.example.ucproomdatabase.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Update
import com.example.ucproomdatabase.JadwalApp
import com.example.ucproomdatabase.ui.view.MataKuliah.DetailMatkulView

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                JadwalApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeDosenViewModel(
                JadwalApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeMatkulViewModel(
                JadwalApp().containerApp.repositoryMatkul
            )
        }

        initializer {
            MataKuliahViewModel(
                JadwalApp().containerApp.repositoryMatkul,
                JadwalApp().containerApp.repositoryDosen
            )
        }

        initializer {
            DetailMkViewModel(
                createSavedStateHandle(),
                JadwalApp().containerApp.repositoryMatkul
            )
        }
        initializer {
            UpdateMKViewModel(
                createSavedStateHandle(),
                JadwalApp().containerApp.repositoryMatkul,
                JadwalApp().containerApp.repositoryDosen
            )
        }
    }
}

fun CreationExtras.JadwalApp(): JadwalApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JadwalApp)