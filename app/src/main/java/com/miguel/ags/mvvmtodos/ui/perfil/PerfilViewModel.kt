package com.miguel.ags.mvvmtodos.ui.perfil

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel.ags.mvvmtodos.model.Usuario
import com.miguel.ags.mvvmtodos.model.UsuarioDatos
import com.miguel.ags.mvvmtodos.ui.Avisos
import kotlinx.coroutines.launch

class PerfilViewModel(private val userDat: UsuarioDatos) : ViewModel(), Observable {

    //Cargamos los usuarios
    val usuario = userDat.usuarioDatos

    //Creamos una variable para actualizar o eliminar
    private var actualizadoOBorrado = false

    private lateinit var usuarioActualizadoOBorrado: Usuario

    @Bindable
    val idUsuario = MutableLiveData<String>()

    @Bindable
    val nameUsuario = MutableLiveData<String>()

    @Bindable
    val emaiUsuario = MutableLiveData<String>()

    @Bindable
    val passUsuario = MutableLiveData<String>()

    @Bindable
    val tokenUsuario = MutableLiveData<String>()

    @Bindable
    val guardarOActualizarBtn = MutableLiveData<String>()

    private val mensajeEstado = MutableLiveData<Avisos<String>>()

    val mensaje: LiveData<Avisos<String>>
        get() = mensajeEstado

    init {
        guardarOActualizarBtn.value = "Guardar"
    }

    fun guardarOActualizar() {
        if (nameUsuario.value == null) {
             mensajeEstado.value = Avisos("Añada el nombre del usuario")
        } else if (emaiUsuario.value == null) {
             mensajeEstado.value = Avisos("Agregue el email del usuario")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emaiUsuario.value!!).matches()) {
            mensajeEstado.value = Avisos("Introduzca el mail de forma correcta")
        } else {
            if (actualizadoOBorrado) {
                usuarioActualizadoOBorrado.name = nameUsuario.value!!
                usuarioActualizadoOBorrado.email = emaiUsuario.value!!
                usuarioActualizadoOBorrado.password = passUsuario.value!!
                usuarioActualizadoOBorrado.token = tokenUsuario.value!!
                actualizar(usuarioActualizadoOBorrado)
            } else {
                val nombre = nameUsuario.value!!
                val email = emaiUsuario.value!!
                val password = passUsuario.value!!
                val token = "2581491684";
                insertar(Usuario(0, nombre, email, password, token))
                nameUsuario.value = null
                emaiUsuario.value = null
                passUsuario.value = null
                tokenUsuario.value = null
            }
        }
    }

    //El viewModelScope.launch se establece cuando en la misma vista se van a realizar operaciones estando este view activo

    fun insertar(usuario: Usuario) = viewModelScope.launch {
        val nuevoIdFila = userDat.insertar(usuario)
        if (nuevoIdFila > -1) {
            mensajeEstado.value = Avisos("Usuario insertado correctamente $nuevoIdFila")
            iniciarActualizaroBorrar(usuario)
        } else {
            mensajeEstado.value = Avisos("Error al insertar usuario")
        }
    }

    fun actualizar(usuario: Usuario) = viewModelScope.launch {
        val numDeFila = userDat.actualizar(usuario)
        if (numDeFila > 0) {
            nameUsuario.value = null
            emaiUsuario.value = null
            passUsuario.value = null
            tokenUsuario.value = null

            actualizadoOBorrado = false
            guardarOActualizarBtn.value = "Guardar"
            mensajeEstado.value = Avisos("$numDeFila actualizado")
            //Mensaje de Borrado
        } else {
            mensajeEstado.value = Avisos("Error")
        }
    }


    fun iniciarActualizaroBorrar(usuario: Usuario) = viewModelScope.launch {
        nameUsuario.value = usuario.name
        emaiUsuario.value = usuario.email
        passUsuario.value = usuario.password
        tokenUsuario.value = usuario.token
        idUsuario.value = usuario.id.toString()

        actualizadoOBorrado = true
        usuarioActualizadoOBorrado = usuario
        guardarOActualizarBtn.value = "Actualizar"
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}