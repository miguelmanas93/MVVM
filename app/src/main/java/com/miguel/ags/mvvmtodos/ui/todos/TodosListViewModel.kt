package com.miguel.ags.mvvmtodos.ui.todos

import androidx.lifecycle.MutableLiveData
import android.view.View
import com.miguel.ags.mvvmtodos.R
import com.miguel.ags.mvvmtodos.base.BaseViewModel
import com.miguel.ags.mvvmtodos.model.Todos
import com.miguel.ags.mvvmtodos.model.TodosDao
import com.miguel.ags.mvvmtodos.network.TodosApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TodosListViewModel(private val todosDao: TodosDao): BaseViewModel(){
    @Inject
    lateinit var todosApi: TodosApi
    val todosListAdapter: TodosListAdapter = TodosListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { cargarTodoss() }

    private lateinit var subscription: Disposable

    init{
        cargarTodoss()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun cargarTodoss(){
        subscription = Observable.fromCallable { todosDao.all }
            .concatMap {
                    dbTodosList ->
                if(dbTodosList.isEmpty())
                    todosApi.getTodos().concatMap {
                            apiTodosList -> todosDao.insertAll(*apiTodosList.toTypedArray())
                        Observable.just(apiTodosList)
                    }
                else
                    Observable.just(dbTodosList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveTodosListStart() }
            .doOnTerminate { onRetrieveTodosListFinish() }
            .subscribe(
                { result -> onRetrieveTodosListSuccess(result) },
                { onRetrieveTodosListError() }
            )
    }

    private fun onRetrieveTodosListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveTodosListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveTodosListSuccess(todosList:List<Todos>){
        todosListAdapter.updateTodosList(todosList)
    }

    private fun onRetrieveTodosListError(){
        errorMessage.value = R.string.todos_error
    }
}