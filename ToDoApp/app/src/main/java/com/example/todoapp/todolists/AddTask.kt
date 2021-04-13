package com.example.todoapp.todolists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.TaskHolder
import com.example.todoapp.todolists.data.Task
import com.example.todoapp.databinding.AddtaskLayoutBinding

class AddTask : AppCompatActivity()  {

    private lateinit var binding: AddtaskLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddtaskLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addTaskButton.setOnClickListener {
            val title = binding.addTask.text.toString()


            todolistCollection.forEach{
                if (it == TaskHolder.pickedToDoList){
                    val newTask = Task(false, title, TaskHolder.pickedToDoList!!.id)

                    it.list.add(newTask)

                    println("111111111${it.list}")
                    progress_update(it.id)
                    finish()
                }
            }
        }
    }
}