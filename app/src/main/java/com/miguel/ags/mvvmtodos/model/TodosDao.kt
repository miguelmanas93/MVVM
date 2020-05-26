package com.miguel.ags.mvvmtodos.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TodosDao {
    @get:Query("SELECT * FROM todos")
    val all: List<Todos>

    @Insert
    fun insertAll(vararg todos: Todos)
}