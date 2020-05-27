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
import com.miguel.ags.mvvmtodos.utils.Avisos
import kotlinx.coroutines.launch

open class PerfilViewModel(private val userDat: UsuarioDatos) : ViewModel(), Observable {

    //Cargamos los usuarios
    val usuario = userDat.usuarioDatos

    //Creamos una variable para actualizar o eliminar
    private var actualizadoOBorrado = false

    var insertado = false

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
            mensajeEstado.value =
                Avisos("Añada el nombre del usuario")
        } else if (emaiUsuario.value == null) {
            mensajeEstado.value =
                Avisos("Agregue el email del usuario")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emaiUsuario.value!!).matches()) {
            mensajeEstado.value =
                Avisos("Introduzca el mail de forma correcta")
        } else {
            if (actualizadoOBorrado) {
                usuarioActualizadoOBorrado.name = nameUsuario.value!!
                usuarioActualizadoOBorrado.email = emaiUsuario.value!!
                usuarioActualizadoOBorrado.password = passUsuario.value!!
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
    //En el caso de insertar varios se irá incrementando el id, como solo va a ser un usuario,
    fun insertar(usuario: Usuario) = viewModelScope.launch {
        if (insertado == false) {
            userDat.insertar(usuario)
            mensajeEstado.value =
                Avisos("Usuario insertado correctamente")
            insertado = true
        } else {
            mensajeEstado.value =
                Avisos("Error al insertar usuario")
        }
    }

    fun actualizar(usuario: Usuario) = viewModelScope.launch {
        if (insertado == true) {
            nameUsuario.value = usuario.name
            emaiUsuario.value = usuario.email
            passUsuario.value = usuario.password

            actualizadoOBorrado = true
            userDat.actualizar(usuario)
            guardarOActualizarBtn.value = "Actualizar"
            mensajeEstado.value =
                Avisos(" Actualizado correctamente")
        } else {
            mensajeEstado.value = Avisos("Error")
        }
    }

    fun iniciarActualizaroBorrar(usuario: Usuario) = viewModelScope.launch {
        nameUsuario.value = usuario.name
        emaiUsuario.value = usuario.email
        actualizadoOBorrado = true
        usuarioActualizadoOBorrado = usuario
        guardarOActualizarBtn.value = "Actualizar"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}