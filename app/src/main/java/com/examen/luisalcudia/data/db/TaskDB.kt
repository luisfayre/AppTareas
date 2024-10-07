package com.examen.luisalcudia.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.examen.luisalcudia.data.dao.TaskDao
import com.examen.luisalcudia.data.model.Task

/***
 * Clase para hacer uso de room
 */

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}