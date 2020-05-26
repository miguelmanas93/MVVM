package com.miguel.ags.mvvmtodos.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.miguel.ags.mvvmtodos.model.Todos
import com.miguel.ags.mvvmtodos.model.TodosDao
import com.miguel.ags.mvvmtodos.model.Usuario
import com.miguel.ags.mvvmtodos.model.UsuarioDao

@Database(entities = [
    Todos::class,
    Usuario::class
    ], version = 3)

abstract class AppDatabase : RoomDatabase() {
    abstract fun todosDao(): TodosDao
    abstract fun usuarioDao(): UsuarioDao

}