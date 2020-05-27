package com.miguel.ags.mvvmtodos.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miguel.ags.mvvmtodos.ui.perfil.PerfilViewModel

class UsuarioViewModelFactory(private val repository: UsuarioDatos): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PerfilViewModel::class.java)){
            return PerfilViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}