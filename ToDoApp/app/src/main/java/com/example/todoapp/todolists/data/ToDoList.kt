package com.example.todoapp.todolists.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ToDoList(var id:Int=0, var list: MutableList<Task>, val title:String, val description:String, var priority:Int, var progress: Int = 0):Parcelable

@Parcelize
data class Task(var check: Boolean, var title: String, var id:Int):Parcelable

