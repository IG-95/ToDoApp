package com.example.todoapp.todolists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.*
import com.example.todoapp.databinding.AddtodolistLayoutBinding
import com.example.todoapp.todolists.data.Task
import com.example.todoapp.todolists.data.ToDoList

class AddToDoList : AppCompatActivity() {
    private lateinit var binding: AddtodolistLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddtodolistLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addToDoListButton.setOnClickListener {
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()
            var list = mutableListOf<Task>()

            val newTodoList = ToDoList(getId(), list, title, description)

            todolistCollection.add(newTodoList)

            ref.child(auth.uid.toString()).setValue(todolistCollection)

            finish()
        }
    }
}