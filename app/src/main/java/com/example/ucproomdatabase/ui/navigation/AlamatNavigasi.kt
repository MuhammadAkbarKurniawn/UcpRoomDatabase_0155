package com.example.ucproomdatabase.ui.navigation

interface AlamatNavigasi {
    val route: String

    object DestinasiHome : AlamatNavigasi {
        override val route = "home"
    }

    object DestinasiHomeDsn : AlamatNavigasi {
        override val route = "homedsn"
    }

    object DestinasiHomeMK : AlamatNavigasi {
        override val route = "homemk"
    }

    object DestinasiDetailDsn : AlamatNavigasi {
        override val route = "detaildsn"
        const val NIM = "nim"
        val routesWithArg = "$route/{$NIM}"
    }

    object DestinasiDetailMK : AlamatNavigasi {
        override val route = "detailmk"
        const val NIM = "nim"
        val routesWithArg = "$route/{$NIM}"
    }

    object DestinasiUpdateMK : AlamatNavigasi {
        override val route = "updatemk"
        const val NIM = "nim"
        val routesWithArg = "$route/{$NIM}"
    }

}