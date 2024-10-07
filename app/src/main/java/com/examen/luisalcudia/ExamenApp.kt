package com.examen.luisalcudia

import android.app.Application
import android.content.Context
import androidx.room.*
import com.examen.luisalcudia.data.db.TaskDatabase
import com.google.firebase.FirebaseApp

class ExamenApp : Application() {

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        // Inicializar DB
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "tareas_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)  // Inicializa Firebase
        getDatabase(this) // Inicializar DB
    }
}
