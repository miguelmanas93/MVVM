package com.miguel.ags.mvvmtodos.injection.component

import com.miguel.ags.mvvmtodos.injection.module.NetworkModule
import com.miguel.ags.mvvmtodos.ui.todos.TodosListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified TodosListViewModel.
     * @param todosListViewModel TodosListViewModel in which to inject the dependencies
     */
    fun inject(todosListViewModel: TodosListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}