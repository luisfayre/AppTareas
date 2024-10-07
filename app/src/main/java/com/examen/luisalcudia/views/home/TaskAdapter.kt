package com.examen.luisalcudia.views.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.examen.luisalcudia.R
import com.examen.luisalcudia.data.model.Task


/***
 * Adaptador para listar tareas en el RecyclerView
 * */

class TaskAdapter(
    private var tasks: List<Task>,
    private val listener: OnTaskClickListener
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // Callback de la tarea seleccionada
    interface OnTaskClickListener {
        fun onEditClick(task: Task)
        fun onTaskCompletedClick(task: Task)
        fun onDeleteClick(task: Task)
    }

    // ViewHolder
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        val taskCompletedCheckBox: CheckBox = itemView.findViewById(R.id.taskCompletedCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.taskTitle.text = task.title
        holder.taskDescription.text = task.desc
        holder.taskCompletedCheckBox.isChecked = task.status

        // Cambio de color si la tarea está completada
        if (task.status) {
            holder.taskTitle.setTextColor(Color.GRAY)
            holder.taskDescription.setTextColor(Color.GRAY)
        } else {
            holder.taskTitle.setTextColor(Color.BLACK)
            holder.taskDescription.setTextColor(Color.BLACK)
        }

        holder.taskCompletedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.status = isChecked
            listener.onTaskCompletedClick(task)
        }

        // Configurar el listener del botónes de edicon
        val deleteButton: Button = holder.itemView.findViewById(R.id.eliminar)
        val editarButton: Button = holder.itemView.findViewById(R.id.editar)
        deleteButton.setOnClickListener {
            listener.onDeleteClick(task)
        }
        editarButton.setOnClickListener {
            listener.onEditClick(task)
        }

    }

    override fun getItemCount() = tasks.size

    // Actualiza la lista de tareas
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}