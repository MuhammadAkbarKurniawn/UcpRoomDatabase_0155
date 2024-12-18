package com.example.apllicationdata.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucproomdatabase.data.dao.MatkulDao
import com.example.ucproomdatabase.data.entity.MataKuliah

// Mendefinisikan Database dengan tabel matkul
@Database(entities = [MataKuliah::class], version = 1, exportSchema = false)
abstract class DatabaseMatkul : RoomDatabase() {

    // Mendefinisikan fungsi untuk mengakses data
    abstract fun matkulDao(): MatkulDao

    companion object {
        @Volatile // Memastikan bahwa nilai variabel instance selalu sama di semua thread
        private var Instance: DatabaseMatkul? = null

        fun getDatabase(context: Context): DatabaseMatkul {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseMatkul::class.java,
                    "DatabaseMatkul"
                )
                    .build().also { Instance = it }
            })
        }
    }
}
