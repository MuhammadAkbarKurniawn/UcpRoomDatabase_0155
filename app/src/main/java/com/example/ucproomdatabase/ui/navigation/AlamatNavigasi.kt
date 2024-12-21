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

    object DestinasiDetailMK : AlamatNavigasi {
        override val route = "detailmk"
        const val KODE = "kode"
        val routesWithArg = "$route/{$KODE}"
    }

    object DestinasiUpdateMK : AlamatNavigasi {
        override val route = "updatemk"
        const val KODE = "kode"
        val routesWithArg = "$route/{$KODE}"
    }

}