package com.example.ucproomdatabase.ui.view.MataKuliah

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase.data.entity.Dosen
import com.example.ucproomdatabase.ui.customwidget.CustomTopBar
import com.example.ucproomdatabase.ui.navigation.AlamatNavigasi
import com.example.ucproomdatabase.ui.viewmodel.FormErrorState
import com.example.ucproomdatabase.ui.viewmodel.MataKuliahViewModel
import com.example.ucproomdatabase.ui.viewmodel.MatkulEvent
import com.example.ucproomdatabase.ui.viewmodel.MatkulUiState
import com.example.ucproomdatabase.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertMk : AlamatNavigasi {
    override val route: String = "Insert_Mk"
}

@Composable
fun InsertMKView(
    onNavigate: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CustomTopBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah MataKuliah"
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                InsertBodyMK(
                    uiState = uiState,
                    dosenList = uiState.dosenList,
                    onValueChange = { updateEvent -> viewModel.updateState(updateEvent) },
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveData()
                        }
                        onNavigate()
                    }
                )
            }
        }
    )
}

@Composable
fun InsertBodyMK(
    modifier: Modifier = Modifier,
    onValueChange: (MatkulEvent) -> Unit,
    uiState: MatkulUiState,
    onClick: () -> Unit,
    dosenList: List<Dosen>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatkul(
            matkulEvent = uiState.matkulEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            dosenList = dosenList
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3) // Set the button background to blue
            )
        ) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMatkul(
    matkulEvent: MatkulEvent = MatkulEvent(),
    onValueChange: (MatkulEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    dosenList: List<Dosen>,
) {
    val jenisMataKuliah = listOf("Wajib", "Peminatan")
    val semester = listOf("Ganjil", "Genap")

    var chosenDropdown by remember { mutableStateOf(matkulEvent.dosenPengampu) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.kode,
            onValueChange = { onValueChange(matkulEvent.copy(kode = it)) },
            label = { Text("Kode") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.kode ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.nama,
            onValueChange = { onValueChange(matkulEvent.copy(nama = it)) },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )
        Text(text = errorState.nama ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.SKS,
            onValueChange = { onValueChange(matkulEvent.copy(SKS = it)) },
            label = { Text("SKS") },
            isError = errorState.SKS != null,
            placeholder = { Text("Masukkan SKS") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.SKS ?: "", color = Color.Red)

        Text(text = "Semester")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            semester.forEach { sms ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matkulEvent.semester == sms,
                        onClick = { onValueChange(matkulEvent.copy(semester = sms)) }
                    )
                    Text(text = sms)
                }
            }
        }
        Text(text = errorState.semester ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis MataKuliah")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenisMataKuliah.forEach { jm ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matkulEvent.jenis == jm,
                        onClick = { onValueChange(matkulEvent.copy(jenis = jm)) }
                    )
                    Text(text = jm)
                }
            }
        }
        Text(text = errorState.jenis ?: "", color = Color.Red)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = chosenDropdown,
                onValueChange = { /* Cannot be changed manually */ },
                label = { Text("Pilih Dosen Pengampu") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand menu"
                    )
                },
                modifier = Modifier
                    .menuAnchor() // Ensures the dropdown menu is anchored below the TextField
                    .fillMaxWidth(),  // Ensures the TextField takes full width
                readOnly = true,
                isError = errorState.dosenPengampu != null
            )

            // Dropdown menu will appear below the TextField and take the full width
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()  // Ensures the dropdown has the same width as the TextField
            ) {
                dosenList.forEach { dosen ->
                    DropdownMenuItem(
                        onClick = {
                            chosenDropdown = dosen.nama
                            expanded = false
                            onValueChange(matkulEvent.copy(dosenPengampu = dosen.nama))
                        },
                        text = { Text(text = dosen.nama) }
                    )
                }
            }
        }
        Text(text = errorState.dosenPengampu ?: "", color = Color.Red)
    }
}
