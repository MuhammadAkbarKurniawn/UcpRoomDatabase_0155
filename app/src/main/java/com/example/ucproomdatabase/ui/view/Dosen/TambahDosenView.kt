package com.example.ucproomdatabase.ui.view.Dosen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase.ui.customwidget.CustomTopBar
import com.example.ucproomdatabase.ui.navigation.AlamatNavigasi
import com.example.ucproomdatabase.ui.viewmodel.DosenEvent
import com.example.ucproomdatabase.ui.viewmodel.DosenUiState
import com.example.ucproomdatabase.ui.viewmodel.DosenViewModel
import com.example.ucproomdatabase.ui.viewmodel.FormErrorState
import com.example.ucproomdatabase.ui.viewmodel.PenyediaViewModel

import kotlinx.coroutines.launch

object DestinasiInsertDsn : AlamatNavigasi {
    override val route: String = "Insert_Dsn"
}
@Composable
fun InsertDsnView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory) // Inisialisasi viewModel
) {
    val uiState = viewModel.uiState // Ambil Uistate dari view model
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan SnackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) // Tampilan Snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // Tempatkan snackbar di Scaffold
        topBar = {
            CustomTopBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Daftar Dosen",
            )
        },
        content = { padding ->
            // Apply padding and ensure full-screen layout with enough space for content
            Column(
                modifier = Modifier
                    .fillMaxSize() // Fill the entire screen
                    .padding(padding) // Add padding around the screen
            ) {
                InsertBodyDsn(
                    uiState = uiState,
                    onValueChange = { updateEvent ->
                        viewModel.updateState(updateEvent) // Update state in ViewModel
                    },
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveData() // Save data
                        }
                        onNavigate()
                    }
                )
            }
        }
    )
}
@Composable
fun InsertBodyDsn(
    modifier: Modifier = Modifier,
    onValueChange: (DosenEvent) -> Unit,
    uiState: DosenUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize() // Ensure the column fills the available space
            .padding(16.dp), // Add padding to avoid UI elements sticking to the edges
        verticalArrangement = Arrangement.Top, // Align form at the top
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormDosen(
            dosenEvent = uiState.dosenEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth() // Ensure form fills the available width
        )

        Spacer(modifier = Modifier.height(16.dp)) // Add space before the button

        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(), // Ensure the button spans the full width
                    colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3) // Set background color to blue
                    )
        ) {
            Text("Simpan")
        }
    }
}


@Composable
fun FormDosen(
    dosenEvent: DosenEvent = DosenEvent(),
    onValueChange: (DosenEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenisKelamin = listOf("Laki-Laki", "Perempuan")

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nama,
            onValueChange = {
                onValueChange(dosenEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama") }
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nidn,
            onValueChange = {
                onValueChange(dosenEvent.copy(nidn = it))
            },
            label = { Text("NIDN") },
            isError = errorState.nidn != null,
            placeholder = { Text("Masukkan NIDN") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.nidn ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Kelamin")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenisKelamin.forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = dosenEvent.jenisKelamin == jk,
                        onClick = {
                            onValueChange(dosenEvent.copy(jenisKelamin = jk))
                        },
                    )
                    Text(
                        text = jk,
                    )
                }
            }
        }
        Text(
            text = errorState.jenisKelamin ?: "",
            color = Color.Red
        )
    }
}
