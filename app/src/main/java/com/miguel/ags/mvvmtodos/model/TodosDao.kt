package com.miguel.ags.mvvmtodos.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodosDao {
    @get:Query("SELECT * FROM todos")
    val all: List<Todos>

    @Insert
    fun insertAll(vararg todos: Todos)
}