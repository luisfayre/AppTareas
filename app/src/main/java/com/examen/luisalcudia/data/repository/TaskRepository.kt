package com.examen.luisalcudia.data.repository

import androidx.lifecycle.LiveData
import com.examen.luisalcudia.data.dao.TaskDao
import com.examen.luisalcudia.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {
    // Metodo para listar las tareas de la db
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    // Metodo para guardar tareas a la db
    suspend fun saveTask(task: Task) {
        taskDao.insertTask(task)
    }

    // Metodo para acutlizar la tarea en la db
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    // Metodo para eliminar la tarea en la db
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}