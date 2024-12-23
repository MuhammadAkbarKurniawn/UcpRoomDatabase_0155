package com.example.ucproomdatabase.ui.view.MataKuliah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase.data.entity.MataKuliah
import com.example.ucproomdatabase.repository.RepositoryMatkul
import com.example.ucproomdatabase.ui.customwidget.CustomTopBar
import com.example.ucproomdatabase.ui.viewmodel.HomeMatkulViewModel
import com.example.ucproomdatabase.ui.viewmodel.HomeUiStateMK
import com.example.ucproomdatabase.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeMKView(
    viewModel: HomeMatkulViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddMK: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            CustomTopBar(
                judul = "Daftar MataKuliah",
                showBackButton = true,
                onBack =  onBack,
                modifier = modifier
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMK,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah MataKuliah"
                )
            }
        }
    ) { innerPadding ->
        val homeUiState by viewModel.homeUiStateMK.collectAsState()

        BodyHomeMkView(
            homeUiStateMK = homeUiState,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeMkView(
    homeUiStateMK: HomeUiStateMK,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } // Snnackbar state
    when {
        homeUiStateMK.isLoading -> {
            //menampilkan indikator Login
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        homeUiStateMK.isError -> {
            //menampilkan pesan eror
            LaunchedEffect(homeUiStateMK) {
                homeUiStateMK.errorMessage?.let {message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message) //TampilanSnackbar
                    }
                }
            }
        }

        homeUiStateMK.listMK.isEmpty() -> {
            //menampilkan pesan jika data kosong
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Tidak ada data MataKuliah",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            //menampilkan daftar matkul
            ListMataKuliah(
                listMK = homeUiStateMK.listMK,
                onClick = {
                    onClick(it)
                    println(it)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListMataKuliah(
    listMK: List<MataKuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
){
    LazyColumn (
        modifier = modifier
    ){
        items(
            items = listMK,
            itemContent = { mk ->
                CardMhs(
                    mk = mk,
                    onClick = { onClick(mk.kode)}
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMhs (
    mk: MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(imageVector = Icons.Filled.Info, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = mk.kode,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = mk.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = mk.dosenPengampu,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}