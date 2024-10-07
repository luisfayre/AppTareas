package com.examen.luisalcudia.views.form_task

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.examen.luisalcudia.data.model.Task
import com.examen.luisalcudia.databinding.ActivityFormTaskBinding
import com.examen.luisalcudia.views.home.TaskViewModel


/***
 * Actividad para agregar nuevas tareas
 * */

class FormTaskActivity  : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var binding: ActivityFormTaskBinding // Link de la vista
    private var task: Task? = null  // Valida si es una tarea

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task = intent.getParcelableExtra("task")

        binding.taskCompletedCheckBox.visibility = View.GONE // Ocultar el chekbox

        // Si hay una tarea, prellenamos el formulario con los datos para editar
        if (task != null) {
            binding.title.setText(task?.title)
            binding.description.setText(task?.desc)
            binding.taskCompletedCheckBox.visibility = View.VISIBLE
            if (task?.status == true) binding.taskCompletedCheckBox.isChecked = true

        }


        binding.saveTask.setOnClickListener {
            val taskTitle = binding.title.text.toString()
            val taskDescription = binding.description.text.toString()
            val taskComplete = binding.taskCompletedCheckBox.isChecked

            if (task == null) {
                if (taskTitle.isNotEmpty()) {
                    val newTask = Task(title = taskTitle, desc = taskDescription, status = false)
                    taskViewModel.saveTask(newTask)
                    finish()
                }
            } else {
                // Actualizar tarea existente
                task?.title = taskTitle
                task?.desc = taskDescription
                task?.status = taskComplete

                if (taskTitle.isNotEmpty()) {
                    taskViewModel.updateTask(task!!)
                    finish()
                }
            }

            // Finalizamos la actividad y volvemos a la lista de tareas
            finish()
        }
    }
}