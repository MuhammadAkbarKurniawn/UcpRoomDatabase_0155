package com.example.ucproomdatabase.ui.view.MataKuliah

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase.data.entity.Dosen
import com.example.ucproomdatabase.ui.customwidget.CustomTopBar
import com.example.ucproomdatabase.ui.viewmodel.FormErrorState
import com.example.ucproomdatabase.ui.viewmodel.MatkulEvent
import com.example.ucproomdatabase.ui.viewmodel.MatkulUiState
import com.example.ucproomdatabase.ui.viewmodel.UpdateMKViewModel
import com.example.ucproomdatabase.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMKView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMKViewModel = viewModel(factory = PenyediaViewModel.Factory)// inisialisasi ViewModel

){
    val uiState = viewModel.updateUiState // ambil ui state dari viewmodel
    val snackbarHostState = remember { SnackbarHostState() } // Snackbarstate
    val coroutineScope = rememberCoroutineScope()

    //Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        println("LaunchEffect triggered")
        uiState.snackBarMessage?.let { message->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching coroutine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // tempatkan snackbar discaffold
        topBar = {
            CustomTopBar(
                judul = "Edit MataKuliah",
                showBackButton = true,
                onBack = onBack,
            )
        }
    ){ padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            //isi body
            InsertBodyMK(
                uiState = uiState,
                onValueChange = { updateEvent->
                    viewModel.updateState(updateEvent) //update state diviewmodel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate() // Navigasi di main thread
                            }
                        }
                    }
                }, dosenList = uiState.dosenList
            )
        }
    }
}