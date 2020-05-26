package com.miguel.ags.mvvmtodos.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface UsuarioDao {
    @get:Query("SELECT * FROM usuario")
    val all: List<Usuario>

    @Insert
    suspend fun insertarUsuario(usuario: Usuario) : Long

    @Update
    suspend fun actualizarUsuario(usuario: Usuario) : Int

    @Query("SELECT * FROM usuario")
    fun getDatosUsuario() : LiveData<List<Usuario>>


}
