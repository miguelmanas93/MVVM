package com.miguel.ags.mvvmtodos.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsuarioDao {

    @Insert
    fun insertarUsuario(usuario: Usuario) : Long

    @Update
    fun actualizarUsuario(usuario: Usuario) : Int

    @Query("SELECT * FROM usuario")
    fun getDatosUsuario() : LiveData<List<Usuario>>





}
