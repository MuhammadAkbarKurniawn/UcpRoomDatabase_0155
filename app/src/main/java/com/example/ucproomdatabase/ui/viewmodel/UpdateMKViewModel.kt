package com.example.ucproomdatabase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase.data.entity.Dosen
import com.example.ucproomdatabase.data.entity.MataKuliah
import com.example.ucproomdatabase.repository.RepositoryDosen
import com.example.ucproomdatabase.repository.RepositoryMatkul
import com.example.ucproomdatabase.ui.navigation.AlamatNavigasi.DestinasiUpdateMK
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMKViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatkul: RepositoryMatkul,
    private val repositoryDosen: RepositoryDosen
) :ViewModel() {

    var dosenList by mutableStateOf<List<Dosen>>(emptyList())
        private set

    var updateUiState by mutableStateOf(MatkulUiState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMK.KODE])

    init {
        viewModelScope.launch {
            updateUiState = repositoryMatkul.getMatkul(_kode)
                .filterNotNull()
                .first()
                .toUiStateMk()
        }
        // Fetch Dosen from repository
        viewModelScope.launch {
            val dosenListFromRepo = repositoryDosen.getAllDosen().first() // Fetch Dosen
            dosenList = dosenListFromRepo
            updateUiState = updateUiState.copy(dosenList = dosenList) // Update UI state with dosenList
        }
    }

    fun updateState(matkulEvent: MatkulEvent) {
        updateUiState = updateUiState.copy(
            matkulEvent = matkulEvent,
        )
    }

    fun validateFields (): Boolean {
        val event = updateUiState.matkulEvent
        val errorState = FormErrorState(
            kode = if(event.kode.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if(event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            SKS = if(event.SKS.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            semester = if(event.semester.isNotEmpty()) null else "Alamat tidak boleh kosong",
            jenis = if(event.jenis.isNotEmpty()) null else "Kelas tidak boleh kosong",
            dosenPengampu = if(event.dosenPengampu.isNotEmpty()) null else "Angkatan tidak boleh kosong",
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUiState.matkulEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatkul.updateMatkul(currentEvent.toMatkulEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Berhasil diupdate",
                        matkulEvent = MatkulEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("SnackBarMessage diatur: ${updateUiState.snackBarMessage}")
                } catch (e:Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        }
        else {
            updateUiState = updateUiState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage() {
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }
}

fun MataKuliah.toUiStateMk(): MatkulUiState = MatkulUiState(
    matkulEvent = this.toDetailUiEvent(),
)