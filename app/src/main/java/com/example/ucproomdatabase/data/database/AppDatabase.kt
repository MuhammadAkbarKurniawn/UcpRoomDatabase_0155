package com.example.ucproomdatabase.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucproomdatabase.data.dao.DosenDao
import com.example.ucproomdatabase.data.dao.MatkulDao
import com.example.ucproomdatabase.data.entity.Dosen
import com.example.ucproomdatabase.data.entity.MataKuliah

// Mendefinisikan Database dengan tabel dosen dan matkul
@Database(
    entities = [Dosen::class, MataKuliah::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Mendefinisikan fungsi untuk mengakses data
    abstract fun DosenDao(): DosenDao
    abstract fun MatkulDao(): MatkulDao

    companion object {
        @Volatile // Memastikan bahwa nilai variabel instance selalu sama di semua thread
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase"
                ).build().also { Instance = it }
            }
        }
    }
}
