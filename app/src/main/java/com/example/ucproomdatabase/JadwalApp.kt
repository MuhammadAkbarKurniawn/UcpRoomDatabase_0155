package com.example.ucproomdatabase

import android.app.Application
import com.example.ucproomdatabase.dependenciesinjection.ContainerApp

class JadwalApp : Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this) // Membuat instance
        // Instance adalah object yang dibuat dari class
    }
}
