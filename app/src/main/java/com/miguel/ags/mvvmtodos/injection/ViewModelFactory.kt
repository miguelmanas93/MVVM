package com.miguel.ags.mvvmtodos.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.appcompat.app.AppCompatActivity

import com.miguel.ags.mvvmtodos.model.database.AppDatabase
import com.miguel.ags.mvvmtodos.ui.todos.TodosListViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodosListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "todos").build()
            @Suppress("UNCHECKED_CAST")
            return TodosListViewModel(db.todosDao()) as T;
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}