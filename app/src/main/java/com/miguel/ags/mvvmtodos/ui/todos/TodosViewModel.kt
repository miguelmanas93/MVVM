package com.miguel.ags.mvvmtodos.ui.todos

import androidx.lifecycle.MutableLiveData
import com.miguel.ags.mvvmtodos.base.BaseViewModel
import com.miguel.ags.mvvmtodos.model.Todos

class TodosViewModel: BaseViewModel() {

    private val todosTitle = MutableLiveData<String>()
    private val todosStatus = MutableLiveData<Boolean>()

    fun bind(todos: Todos){
        todosTitle.value = todos.title
        todosStatus.value = todos.completed
    }

    fun getTodosTitle():MutableLiveData<String>{
        return todosTitle
    }

    fun getTodosStatus():MutableLiveData<Boolean>{
        return todosStatus
    }

}