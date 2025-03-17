package com.example.todoapp

import android.app.Application
import androidx.room.Room
import com.example.todoapp.db.ToDoDatabase

class MainApplication : Application() {

    companion object {
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(application: Application): ToDoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext,
                    ToDoDatabase::class.java,
                    "TODO_DB" // Sabit String yerine doğrudan veritabanı ismi
                ).fallbackToDestructiveMigration() // Eski verileri silip yeni şema oluşturur
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = getDatabase(this)
    }
}
