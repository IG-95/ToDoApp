package com.example.todoapp.todolists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.databinding.AddtaskLayoutBinding
import com.example.todoapp.databinding.AddtodolistLayoutBinding
import com.example.todoapp.getId
import com.example.todoapp.todolists.data.Task
import com.example.todoapp.todolists.data.ToDoList
import kotlinx.android.synthetic.main.addtask_layout.*

class AddToDoList : AppCompatActivity(){
    private lateinit var binding: AddtodolistLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddtodolistLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addToDoListButton.setOnClickListener {
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()
            val priority = binding.priority.text.toString().toInt()
            var list = mutableListOf<Task>()

            val newTodoList = ToDoList(getId(), list, title, description, priority)

            todolistCollection.add(newTodoList)
            println("777777777${todolistCollection}")
            finish()

            //addToDoList(list = list, title = title, description = description, published =  priority)
        }
    }
}