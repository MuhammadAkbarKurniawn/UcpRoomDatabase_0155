package com.example.apllicationdata.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucproomdatabase.data.dao.DosenDao
import com.example.ucproomdatabase.data.entity.Dosen

// Mendefinisikan Database dengan tabel dosen
@Database(entities = [Dosen::class], version = 1, exportSchema = false)
abstract class DatabaseDosen : RoomDatabase() {

    // Mendefinisikan fungsi untuk mengakses data
    abstract fun dosenDao(): DosenDao

    companion object {
        @Volatile // Memastikan bahwa nilai variabel instance selalu sama di semua thread
        private var Instance: DatabaseDosen? = null

        fun getDatabase(context: Context): DatabaseDosen {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseDosen::class.java,
                    "DatabaseDosen"
                )
                    .build().also { Instance = it }
            })
        }
    }
}
