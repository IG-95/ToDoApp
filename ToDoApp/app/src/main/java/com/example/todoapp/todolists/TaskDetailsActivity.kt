package com.example.todoapp.todolists

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.TaskHolder
import com.example.todoapp.EXTRA_TODOLIST_INFO
import com.example.todoapp.todolists.data.ToDoList
import com.example.todoapp.todolists.data.Task
import com.example.todoapp.databinding.ActivityTodolistDetailsBinding
import com.example.todoapp.databinding.TaskLayoutBinding

lateinit var displayTaskList: MutableList<Task>


class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodolistDetailsBinding
    private lateinit var task: ToDoList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodolistDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AddTaskBtn.setOnClickListener {
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }


        updateDisplay()

        // Metode med Intent
        //val receivedBook = intent.getParcelableExtra<Book>(EXTRA_BOOK_INFO)

        // Metode uten Intent
        //val receivedTask = TaskHolder.pickedToDoList

        /*if (receivedTask != null) {
            binding.titlepage.text = receivedTask.title
            binding.subtitlepage.text = receivedTask.description
        }*/

        /*if (receivedTask != null) {
            task = receivedTask
            Log.i("Details view", receivedTask.toString())
        } else {
            setResult(RESULT_CANCELED, Intent(EXTRA_TODOLIST_INFO).apply {
                // Legg til info vi vil sende tilbake til Main
            })
            finish() // stenger aktiviteten
        }*/
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


        binding.TaskListing.layoutManager = LinearLayoutManager(this)
        binding.TaskListing.adapter =
            TaskAdapter(displayTaskList, this::checkboxChecked, this::onTaskRemoved)

        println("222222222${todolistCollection}")
    }

    // Function for Task Remove
    private fun onTaskRemoved(task: Task) {
        todolistCollection.forEach {
            if (it.id == TaskHolder.pickedToDoList?.id) {
                it.list.remove(task)
            }
        }
        progress_update(task.id)
        updateDisplay()
    }


    override fun onStart() {
        updateDisplay()
        super.onStart()
    }

    // Checkbox & Progressbar
    private fun checkboxChecked(task: Task): Unit {
        //val checkBinding: TaskLayoutBinding
        //checkBinding = TaskLayoutBinding.inflate(LayoutInflater.from(this))
        todolistCollection.forEach {
            if (it.id == TaskHolder.pickedToDoList?.id) {
                task.check = !task.check
            }
            progress_update(task.id)
            updateDisplay()
        }

        println("Outside of check function ${todolistCollection}")

    }
}

fun progress_update(int: Int){
    todolistCollection.forEach {
        if(it.id == int) {
            var total = it.list.count()
            var checked = 0

            it.list.forEach { task ->
                if(task.check){
                    checked++
                }
            }
            if(total != 0){
                val percent: Int = checked * 100 / total
                it.progress = percent
                total = 0
            }else{
                it.progress = 0
            }
        }
    }

}