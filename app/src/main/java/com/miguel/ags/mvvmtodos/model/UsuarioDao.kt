package com.miguel.ags.mvvmtodos.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsuarioDao {

  //  @Insert
   // suspend fun insertarUsuario(usuario: Usuario) : Long

  //  @Update
   // suspend fun actualizarUsuario(usuario: Usuario) : Int

    @Query("SELECT * FROM usuario")
    fun getDatosUsuario() : LiveData<List<Usuario>>
    //   val all: List<Usuario>





}
