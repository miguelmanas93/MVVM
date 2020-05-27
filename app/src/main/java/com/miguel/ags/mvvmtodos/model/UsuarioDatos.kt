package com.miguel.ags.mvvmtodos.model

class UsuarioDatos(private val dao: UsuarioDao) {

    val usuarioDatos = dao.getDatosUsuario();

    fun insertar(usuario: Usuario): Long {
        return dao.insertarUsuario(usuario)
    }

    fun actualizar(usuario: Usuario): Int {
        return dao.actualizarUsuario(usuario)
    }
}