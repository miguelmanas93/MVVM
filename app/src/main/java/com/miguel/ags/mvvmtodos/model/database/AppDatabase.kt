package com.miguel.ags.mvvmtodos.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miguel.ags.mvvmtodos.model.Todos
import com.miguel.ags.mvvmtodos.model.TodosDao
import com.miguel.ags.mvvmtodos.model.Usuario
import com.miguel.ags.mvvmtodos.model.UsuarioDao

@Database(
    entities = [
        Todos::class,
        Usuario::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todosDao(): TodosDao

    abstract val usuarioDao: UsuarioDao

    //Instancia la base de datos en ROOM
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "usuario"
                    ).allowMainThreadQueries().build()
                }
                return instance
            }
        }

    }



}

