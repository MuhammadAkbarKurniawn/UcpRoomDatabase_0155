package com.example.ucproomdatabase.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase.data.entity.Dosen
import com.example.ucproomdatabase.repository.RepositoryDosen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDosenViewModel (
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    val homeUiStateDosen: StateFlow<HomeUiStateDsn> = repositoryDosen.getAllDosen()
        .filterNotNull()
        .map {
            HomeUiStateDsn(
                listDsn = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit (HomeUiStateDsn(isLoading = true))
            delay(900)
        }
        .catch {
            HomeUiStateDsn(
                isLoading = false,
                isError = true,
                errorMessage = it.message ?: "Terjadi Kesalahan"
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed (5000),
            initialValue = HomeUiStateDsn(
                isLoading = true,
            )
        )
}

data class HomeUiStateDsn(
    val listDsn:List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)