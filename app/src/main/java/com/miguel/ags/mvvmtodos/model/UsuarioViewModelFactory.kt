package com.miguel.ags.mvvmtodos.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miguel.ags.mvvmtodos.ui.todos.LoginViewModel
import java.lang.IllegalArgumentException

class UsuarioViewModelFactory(private val repository: UsuarioDatos): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}