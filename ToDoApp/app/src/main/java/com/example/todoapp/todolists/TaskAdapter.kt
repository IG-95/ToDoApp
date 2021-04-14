package com.example.todoapp.todolists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.todolists.data.Task
import com.example.todoapp.databinding.TaskLayoutBinding
import com.example.todoapp.todolists.data.ToDoList

class TaskAdapter(private var tasks: List<Task>, private var checkboxChecked: (Task) -> Unit, private val onTaskClickRemoved: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    class ViewHolder(private val binding: TaskLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, checkboxChecked: (Task) -> Unit, onTaskClickRemoved: (Task) -> Unit) {
            binding.checkBox.isChecked = task.check
            binding.subTitle.text = task.title
            binding.taskDeleteButton.setOnClickListener {
                onTaskClickRemoved(task)
            }
            binding.checkBox.setOnClickListener {
                checkboxChecked(task)
            }
        }

    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, checkboxChecked, onTaskClickRemoved)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }


}

