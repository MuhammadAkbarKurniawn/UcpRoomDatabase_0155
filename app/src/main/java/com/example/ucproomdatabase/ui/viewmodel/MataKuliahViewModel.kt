package com.example.ucproomdatabase.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase.data.entity.Dosen
import com.example.ucproomdatabase.data.entity.MataKuliah
import com.example.ucproomdatabase.repository.RepositoryDosen
import com.example.ucproomdatabase.repository.RepositoryMatkul
import kotlinx.coroutines.launch

class MataKuliahViewModel(
    private val repositoryMatkul: RepositoryMatkul,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    var uiState by mutableStateOf(MatkulUiState())
        private set

    var matkulList by mutableStateOf<List<MataKuliah>>(emptyList())
        private set

    var dosenList by mutableStateOf<List<Dosen>>(emptyList())
        private set

    init {
        // Fetch MataKuliah from repository
        viewModelScope.launch {
            repositoryMatkul.getAllMatkul().collect { matkulList ->
                this@MataKuliahViewModel.matkulList = matkulList
            }
        }

        // Fetch Dosen from repository
        viewModelScope.launch {
            repositoryDosen.getAllDosen().collect { dosenList ->
                this@MataKuliahViewModel.dosenList = dosenList
                updateUiState()
            }
        }
    }

    fun updateState(matkulEvent: MatkulEvent) {
        uiState = uiState.copy(matkulEvent = matkulEvent)
    }

    fun validateFields(): Boolean {
        val event = uiState.matkulEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            SKS = if (event.SKS.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.matkulEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatkul.insertMatkul(currentEvent.toMatkulEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        matkulEvent = MatkulEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(snackBarMessage = "Data gagal disimpan: ${e.message}")
                }
            }
        } else {
            uiState = uiState.copy(snackBarMessage = "Input tidak valid, periksa kembali data Anda.")
        }
    }

    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }

    private fun updateUiState() {
        uiState = uiState.copy(dosenList = dosenList)
    }
}

data class MatkulUiState(
    val matkulEvent: MatkulEvent = MatkulEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
    val dosenList: List<Dosen> = emptyList()
)

data class MatkulEvent(
    val kode: String = "",
    val nama: String = "",
    val SKS: String = "",
    val semester: String = "",
    val jenis: String = "",
    val dosenPengampu: String = ""
)

fun MatkulEvent.toMatkulEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    SKS = SKS,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)
