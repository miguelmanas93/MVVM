package com.miguel.ags.mvvmtodos.model

class UsuarioDatos(private val dao : UsuarioDao) {

    val usuarioDatos = dao.getDatosUsuario();

   // suspend fun insertar(usuario: Usuario): Long {
    //    return dao.insertarUsuario(usuario)
   // }

 //   suspend fun actualizar(usuario: Usuario):Int{
  //      return dao.actualizarUsuario(usuario)
   // }
}