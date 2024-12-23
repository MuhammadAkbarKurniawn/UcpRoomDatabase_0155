package com.example.ucproomdatabase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucproomdatabase.ui.navigation.AlamatNavigasi.DestinasiDetailMK
import com.example.ucproomdatabase.ui.navigation.AlamatNavigasi.DestinasiHome
import com.example.ucproomdatabase.ui.navigation.AlamatNavigasi.DestinasiHomeDsn
import com.example.ucproomdatabase.ui.navigation.AlamatNavigasi.DestinasiHomeMK
import com.example.ucproomdatabase.ui.navigation.AlamatNavigasi.DestinasiUpdateMK
import com.example.ucproomdatabase.ui.view.Dosen.DestinasiInsertDsn
import com.example.ucproomdatabase.ui.view.Dosen.HomeDosenView
import com.example.ucproomdatabase.ui.view.Dosen.InsertDsnView
import com.example.ucproomdatabase.ui.view.HomeView
import com.example.ucproomdatabase.ui.view.MataKuliah.DestinasiInsertMk
import com.example.ucproomdatabase.ui.view.MataKuliah.DetailMatkulView
import com.example.ucproomdatabase.ui.view.MataKuliah.HomeMKView
import com.example.ucproomdatabase.ui.view.MataKuliah.InsertMKView
import com.example.ucproomdatabase.ui.view.MataKuliah.UpdateMKView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = DestinasiHome.route) {
        // Halaman Home
        composable(route = DestinasiHome.route) {
            HomeView(
                onClickDosen = { navController.navigate(DestinasiHomeDsn.route) },
                onClickMataKuliah = { navController.navigate(DestinasiHomeMK.route) }
            )
        }

        // Halaman Mata Kuliah
        composable(route = DestinasiHomeMK.route) {
            HomeMKView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetailMK.route}/$kode")
                },
                onAddMK = {
                    navController.navigate(DestinasiInsertMk.route)
                },
                onBack = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsertMk.route) {
            InsertMKView(
                onNavigate = { navController.popBackStack() },
                onBack = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(
            DestinasiDetailMK.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMK.KODE){
                    type = NavType.StringType
                }
            )
        ) {
            val kode = it.arguments?.getString(DestinasiDetailMK.KODE)
            kode.let {kode ->
                DetailMatkulView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMK.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // Halaman Dosen
        composable(route = DestinasiHomeDsn.route) {
            HomeDosenView(
                onAddDsn = {
                    navController.navigate(DestinasiInsertDsn.route)
                },
                onBack = { navController.popBackStack() },
                modifier = modifier

            )
        }

        composable(route = DestinasiInsertDsn.route) {
            InsertDsnView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(
            DestinasiUpdateMK.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateMK.KODE) {
                    type = NavType.StringType })
        )
        {

            UpdateMKView(
                onNavigate = { navController.popBackStack() },
                onBack = { navController.popBackStack() },
                modifier = modifier
            )
        }

    }
}