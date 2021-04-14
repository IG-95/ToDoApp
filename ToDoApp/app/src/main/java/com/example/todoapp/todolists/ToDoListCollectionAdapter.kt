import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.todolists.data.ToDoList
import com.example.todoapp.databinding.TodolistLayoutBinding

class ToDoListCollectionAdapter(private var todolist: MutableList<ToDoList>, private val onToDoListClicked: (ToDoList) -> Unit, private val onToDoListClickRemoved: (ToDoList) -> Unit) : RecyclerView.Adapter<ToDoListCollectionAdapter.ViewHolder>() {

    class ViewHolder(val binding: TodolistLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todolist: ToDoList, onToDoListClicked: (ToDoList) -> Unit, onToDoListClickRemoved: (ToDoList) -> Unit) {
            binding.title.text = todolist.title
            binding.description.text = todolist.description
            binding.progressBar.progress = todolist.progress
            binding.card.setOnClickListener {
                onToDoListClicked(todolist)
            }
            binding.ToDoListDeleteButton.setOnClickListener {
                onToDoListClickRemoved(todolist)
            }
        }
    }

    override fun getItemCount(): Int = todolist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todolist = todolist[position]
        holder.bind(todolist, onToDoListClicked, onToDoListClickRemoved)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TodolistLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}