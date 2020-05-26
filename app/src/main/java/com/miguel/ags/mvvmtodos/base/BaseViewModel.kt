package com.miguel.ags.mvvmtodos.base

import androidx.lifecycle.ViewModel
import com.miguel.ags.mvvmtodos.injection.component.DaggerViewModelInjector
import com.miguel.ags.mvvmtodos.injection.component.ViewModelInjector
import com.miguel.ags.mvvmtodos.injection.module.NetworkModule
import com.miguel.ags.mvvmtodos.ui.todos.TodosListViewModel

abstract class BaseViewModel:ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()
    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is TodosListViewModel -> injector.inject(this)
        }
    }
}