package com.example.todoapp

import ToDoListCollectionAdapter
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.todolists.*
import com.example.todoapp.todolists.data.Task
import com.example.todoapp.todolists.data.ToDoList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


const val REQUEST_TODOLIST_DETAILS: Int = 719

var todolistCollection = mutableListOf<ToDoList>()

// Metode uten intents
class TaskHolder {
    companion object {
        var pickedToDoList: ToDoList? = null
    }
}

// Firebase
val auth = Firebase.auth
val ref = FirebaseDatabase.getInstance("https://todoapp-7809a-default-rtdb.europe-west1.firebasedatabase.app/").getReference("/List")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Firebase
    private val TAG: String = "todoapp.MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase
        signInAnonymously()
        download()

        updateDisplay()

        binding.addToDoListBtn.setOnClickListener {
            val intent = Intent(this, AddToDoList::class.java)
            startActivity(intent)
        }
    }

    private fun signInAnonymously() {
        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "Login successful ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Login failed", it)
        }
    }

    // Updates the new list
    private fun updateDisplay() {
        binding.todolistListing.layoutManager = LinearLayoutManager(this)
        binding.todolistListing.adapter = ToDoListCollectionAdapter(todolistCollection, this::onToDoListClicked, this::onToDoListRemoved)
    }

    // Runs the "updateDisplay" and refreshes the list
    override fun onStart() {
        updateDisplay()
        super.onStart()

    }

    private fun onToDoListClicked(todolist: ToDoList): Unit {
        // Metode uten intents
        TaskHolder.pickedToDoList = todolist
        val intent = Intent(this, TaskDetailsActivity::class.java)

        startActivity(intent)
    }

    private fun onToDoListRemoved(todolist: ToDoList) {
        todolistCollection.remove(todolist)

        ref.child(auth.uid.toString()).setValue(todolistCollection)

        updateDisplay()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TODOLIST_DETAILS) {
        }
    }

    // Downloading data from Firebase
    private fun download() {
        val get = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                todolistCollection.clear()
                p0.children.forEach {
                    val id = it.child("id").value.toString().toInt()
                    val tit = it.child("title").value.toString()
                    val desc = it.child("description").value.toString()
                    val prog = it.child("progress").value.toString().toInt()
                    val list = it.child("list")
                    val tasklist = mutableListOf<Task>()
                    if (list.children.count() != 0) {
                        list.children.forEach { d ->
                            val sub_check = d.child("check").value.toString().toBoolean()
                            val sub_tit = d.child("title").value.toString()
                            val sub_id = d.child("id").value.toString().toInt()
                            val task = Task(sub_check, sub_tit, sub_id)
                            tasklist.add(task)
                        }
                    }
                    val newtodolist = ToDoList(id, tasklist, tit, desc, prog)
                    todolistCollection.add(newtodolist)
                }
                updateDisplay()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        ref.child(auth.uid.toString()).addValueEventListener(get)
        ref.child(auth.uid.toString()).addListenerForSingleValueEvent(get)
    }

}

var id: Int = 0

@JvmName("getId1")
fun getId(): Int {
    id++
    return (id)
}


