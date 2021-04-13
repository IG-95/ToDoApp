package com.example.todoapp.todolists

import com.example.todoapp.todolists.data.ToDoList
import com.example.todoapp.todolists.data.Task
import com.example.todoapp.getId

lateinit var todolistCollection: MutableList<ToDoList>

class ToDoListDepositoryManager {


    var onToDoLists: ((MutableList<ToDoList>) -> Unit)? = null
    var onToDoListsUpdate:((task:ToDoList)-> Unit)? = null


    fun load(){

        todolistCollection = mutableListOf(
        ToDoList(getId(), mutableListOf<Task>() ,"Task 1 ", "Description 1", 1),
        ToDoList(getId(), mutableListOf<Task>() ,"Task 2 ", "Description 2", 2)
        )

        onToDoLists?.invoke(todolistCollection)
    }

    fun updateToDoList(todolist: ToDoList){
        // Finner bok og erstatter den
        onToDoListsUpdate?.invoke(todolist)
    }

    fun addToDoList(todolist: ToDoList){
        todolistCollection.add(todolist)
        onToDoLists?.invoke(todolistCollection)
    }

    companion object{
        val instance = ToDoListDepositoryManager()
    }
}
