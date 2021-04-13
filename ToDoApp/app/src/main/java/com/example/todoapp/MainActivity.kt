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
import com.example.todoapp.todolists.data.ToDoList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


const val EXTRA_TODOLIST_INFO: String = "com.example.todoapp.task.info"
const val REQUEST_TODOLIST_DETAILS: Int = 719

//Firebase
//val ref = FirebaseDatabase.getInstance().getReference("Lists")

// Metode uten intents
class TaskHolder{
    companion object {
        var pickedToDoList: ToDoList? = null
    }
}

val auth = Firebase.auth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Firebase crismo
    var onSave:((file:Uri) -> Unit)? = null
    private val TAG:String = "todoapp.MainActivity"

    // Firebase indian
    //var firebaseAuth: FirebaseAuth? = null
    //var firebaseDatabase: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase crismo
        //auth = Firebase.auth
        signInAnonymously()

        // Firebase indian
        /*firebaseAuth = FirebaseAuth.getInstance()
        val databaseReference = FirebaseDatabase.getInstance().getReference(firebaseAuth!!.uid!!)
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(this@MainActivity, snapshot.key, Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })*/

        binding.todolistListing.layoutManager = LinearLayoutManager(this)
        binding.todolistListing.adapter = ToDoListCollectionAdapter(emptyList<ToDoList>(), this::onToDoListClicked, this::onToDoListRemoved)

        ToDoListDepositoryManager.instance.onToDoLists = {
            (binding.todolistListing.adapter as ToDoListCollectionAdapter).updateCollection(it)
        }

        ToDoListDepositoryManager.instance.load()

        binding.addToDoListBtn.setOnClickListener {
            val intent = Intent(this, AddToDoList::class.java)
            startActivity(intent)
        }
    }


    /*private fun addToDoList(id: Int= getId(), list: MutableList<Task>, title: String, description: String, published: Int) {
        val list = ToDoList(id, list, title, description, published)
        TaskDepositoryManager.instance.addToDoList(list)
    }*/

    // Firebase
    /*private fun upload(file: Uri){
        Log.d(TAG, "Upload file $file")

        val ref = FirebaseStorage.getInstance().reference.child("todolist/${file.lastPathSegment}")
        var uploadTask = ref.putFile(file)

        uploadTask.addOnSuccessListener {
            Log.d(TAG, "Saved file ${it.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Error saving file", it)
        }
    }*/

    private fun signInAnonymously(){
        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "Login successful ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Login failed", it)
        }
    }

    // Updates the new list
    private fun updateDisplay(){
        binding.todolistListing.layoutManager = LinearLayoutManager(this)
        binding.todolistListing.adapter = ToDoListCollectionAdapter(todolistCollection, this::onToDoListClicked, this::onToDoListRemoved)
    }

    // Runs the "updateDisplay" and refreshes the list
    override fun onStart() {
        updateDisplay()
        super.onStart()

    }

    private fun onToDoListClicked(todolist: ToDoList):Unit{
        // Detalje side for bok

        // Metode med intents

        /*val intent = Intent(this, BookDetailsActivity::class.java).apply {
            putExtra(EXTRA_BOOK_INFO, book)
            }*/

        // Metode uten intents

        TaskHolder.pickedToDoList = todolist
        val intent = Intent(this, TaskDetailsActivity::class.java)

        startActivity(intent)

        // Hvordan motta ting tilbake gjennom intents
        //startActivityForResult(intent, 719)
    }

    private fun onToDoListRemoved(todolist: ToDoList){
        todolistCollection.remove(todolist)
        updateDisplay()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TODOLIST_DETAILS){

        }
    }
}

var id: Int = 0

@JvmName("getId1")
fun getId(): Int {
    id ++
    return(id)
}