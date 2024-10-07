package com.examen.luisalcudia.views.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.examen.luisalcudia.data.model.Task
import com.examen.luisalcudia.databinding.ActivityHomeBinding
import com.examen.luisalcudia.views.auth.AuthActivity
import com.examen.luisalcudia.views.auth.AuthViewModel
import com.examen.luisalcudia.views.form_task.FormTaskActivity

/***
 * Actividad para visualizar el listado de tareas
 * */
class HomeActivity : AppCompatActivity(), TaskAdapter.OnTaskClickListener {

    private val taskViewModel: TaskViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding // Link de la vista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TaskAdapter(emptyList(), this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel.allTasks.observe(this, Observer { tasks ->
            tasks?.let { adapter.updateTasks(it) }
        })

        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, FormTaskActivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener{
            authViewModel.logout()
            navigateTologin()
        }
    }
    // Método para editar la tarea
    override fun onEditClick(task: Task) {
        val intent = Intent(this, FormTaskActivity::class.java)
        intent.putExtra("task", task)  // Pasar el objeto Task
        startActivity(intent)
    }

    // Método para eliminar la tarea
    override fun onDeleteClick(task: Task) {
        taskViewModel.deleteTask(task)
    }
    // Método para completar la tarea
    override fun onTaskCompletedClick(task: Task) {
        task.status = task.status
        taskViewModel.updateTask(task)
    }

    fun navigateTologin(){
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // Limpiar la pila de actividades
        startActivity(intent)
        finish()
    }

}