package com.example.todoapp.todolists

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.*
import com.example.todoapp.todolists.data.ToDoList
import com.example.todoapp.todolists.data.Task
import com.example.todoapp.databinding.ActivityTodolistDetailsBinding

lateinit var displayTaskList: MutableList<Task>


class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodolistDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodolistDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AddTaskBtn.setOnClickListener {
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }

        updateDisplay()
    }

    fun updateDisplay() {
        // Update Task List / Activity Main
        displayTaskList = mutableListOf<Task>()

        todolistCollection.forEach {
            if (it.id == TaskHolder.pickedToDoList?.id) {
                it.list.forEach { subTask ->
                    displayTaskList.add(subTask)
                }
                binding.titlepage.text = it.title
                binding.subtitlepage.text = it.description
                binding.ListProgressBar.progress = it.progress
            }
        }


        binding.TaskListing.adapter =
                TaskAdapter(displayTaskList, this::checkboxChecked, this::onTaskRemoved)

        binding.TaskListing.layoutManager = LinearLayoutManager(this)

    }

    // Function for Task Remove
    private fun onTaskRemoved(task: Task) {
        todolistCollection.forEach {
            if (it.id == TaskHolder.pickedToDoList?.id) {
                it.list.remove(task)
            }
        }
        progress_update(task.id)

        ref.child(auth.uid.toString()).setValue(todolistCollection)

        updateDisplay()
    }


    override fun onStart() {
        updateDisplay()
        super.onStart()
    }

    // Checkbox & Progressbar
    private fun checkboxChecked(task: Task): Unit {
        todolistCollection.forEach {
            if (it.id == TaskHolder.pickedToDoList?.id) {
                task.check = !task.check
            }
            progress_update(task.id)

        }
        ref.child(auth.uid.toString()).setValue(todolistCollection)

        updateDisplay()
    }
}

fun progress_update(int: Int) {
    todolistCollection.forEach {
        if (it.id == int) {
            var total = it.list.count()
            var checked = 0

            it.list.forEach { task ->
                if (task.check) {
                    checked++
                }
            }
            if (total != 0) {
                val percent: Int = checked * 100 / total
                it.progress = percent
                total = 0
            } else {
                it.progress = 0
            }
        }
    }

}