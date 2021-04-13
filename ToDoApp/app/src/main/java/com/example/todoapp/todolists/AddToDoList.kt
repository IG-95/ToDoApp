package com.example.todoapp.todolists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.auth
import com.example.todoapp.databinding.AddtaskLayoutBinding
import com.example.todoapp.databinding.AddtodolistLayoutBinding
import com.example.todoapp.getId
import com.example.todoapp.ref
import com.example.todoapp.todolistCollection
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
            var list = mutableListOf<Task>()

            val newTodoList = ToDoList(getId(), list, title, description)

            todolistCollection.add(newTodoList)
            println("777777777${todolistCollection}")

            //ref.setValue("95")
            //ref.setValue(todolistCollection)
            ref.child(auth.uid.toString()).setValue(todolistCollection)

            finish()
        }
    }
}