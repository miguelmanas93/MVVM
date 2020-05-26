package com.miguel.ags.mvvmtodos.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity

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