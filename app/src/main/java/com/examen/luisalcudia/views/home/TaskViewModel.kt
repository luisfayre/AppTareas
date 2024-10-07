package com.examen.luisalcudia.views.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.examen.luisalcudia.ExamenApp
import com.examen.luisalcudia.data.model.Task
import com.examen.luisalcudia.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        val taskDao = ExamenApp.getDatabase(application).taskDao() // Inicializa la db
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    // Metodo para guardar la tarea
    fun saveTask(task: Task) = viewModelScope.launch {
        repository.saveTask(task)
    }

    // Metodo para actualizar la tarea
    fun updateTask(task: Task) = viewModelScope.launch {
        Log.wtf("task","$task")
        repository.updateTask(task)
    }

    // Metodo para eliminar la tarea
    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }
}