package com.miguel.ags.mvvmtodos.ui.todos

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.miguel.ags.mvvmtodos.R
import com.miguel.ags.mvvmtodos.databinding.ItemTodosBinding
import com.miguel.ags.mvvmtodos.model.Todos


class TodosListAdapter: RecyclerView.Adapter<TodosListAdapter.ViewHolder>() {
    private lateinit var todosList:List<Todos>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosListAdapter.ViewHolder {
        val binding: ItemTodosBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_todos, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodosListAdapter.ViewHolder, position: Int) {
        holder.bind(todosList[position])
    }

    override fun getItemCount(): Int {
        return if(::todosList.isInitialized) todosList.size else 0
    }

    fun updateTodosList(todosList:List<Todos>){
        this.todosList = todosList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemTodosBinding): RecyclerView.ViewHolder(binding.root){
        private val viewModel = TodosViewModel()

        fun bind(todos:Todos){
            viewModel.bind(todos)
            binding.viewModel = viewModel
           }
    }
}