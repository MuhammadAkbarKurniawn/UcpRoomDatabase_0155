package com.example.ucproomdatabase.ui.viewmodel


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase.data.entity.MataKuliah
import com.example.ucproomdatabase.repository.RepositoryMatkul
import com.example.ucproomdatabase.ui.navigation.AlamatNavigasi.DestinasiDetailMK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMkViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMatkul: RepositoryMatkul,

    ) : ViewModel() {
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiDetailMK.KODE])

    val detailUiState: StateFlow<DetailUiState> = repositoryMatkul.getMatkul(_kode)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(500)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )

    fun deleteMk() {
        detailUiState.value.detailUiEvent.toMatkulEntity().let {
            viewModelScope.launch {
                repositoryMatkul.deleteMatkul(it)
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: MatkulEvent = MatkulEvent(),
    val isLoading: Boolean = false,
    val isError : Boolean = false,
    val errorMessage: String = ""

){
    val isUiEventEmpty:Boolean
        get() = detailUiEvent == MatkulEvent()

    val isUiEventNotEmpty:Boolean
        get() = detailUiEvent != MatkulEvent()
}

fun MataKuliah.toDetailUiEvent(): MatkulEvent {
    return MatkulEvent(
        kode = kode,
        nama = nama,
        SKS  = SKS,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu

    )
}
