package com.miguel.ags.mvvmtodos.ui.todos

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Bindable
import android.databinding.Observable
import android.util.Patterns

import com.miguel.ags.mvvmtodos.model.Usuario
import com.miguel.ags.mvvmtodos.model.UsuarioDatos

class LoginViewModel(private val usuarioDatos: UsuarioDatos) : ViewModel(), Observable {

    //Cargamos los usuarios
    val usuario = usuarioDatos.usuarioDatos

    //Creamos una variable para actualizar o eliminar
    private var actualizadoOBorrado = false

    private lateinit var usuarioActualizadoOBorrado: Usuario

    @Bindable
    val nameUsuario = MutableLiveData<String>()

    @Bindable
    val emaiUsuario = MutableLiveData<String>()

    @Bindable
    val passUsuario = MutableLiveData<String>()

    @Bindable
    val guardarOActualizarBtn = MutableLiveData<String>()

    init {
        guardarOActualizarBtn.value = "Guardar"
    }

    fun guardarOActualizar() {
        if (nameUsuario.value == null) {
           // Toast.makeText(this, "Añada el nombre", Toast.LENGTH_LONG).show()

        } else if (emaiUsuario.value == null) {
          //  mensajeEstado.value = Eventos("Agregue el email del suscriptor")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emaiUsuario.value!!).matches()) {
           // mensajeEstado.value = Eventos("Introduzca el mail de forma correcta")
        } else {
            if (actualizadoOBorrado) {
                usuarioActualizadoOBorrado.name =  nameUsuario.value!!
                usuarioActualizadoOBorrado.email = emaiUsuario.value!!
                actualizar(usuarioActualizadoOBorrado)
            } else {
                val nombre = nameUsuario.value!!
                val email = emaiUsuario.value!!
                val password = passUsuario.value!!
                insertar(Usuario(0, nombre, email, password, "2581491684"))
                nameUsuario.value = null
                emaiUsuario.value = null
                passUsuario.value = null

            }
        }
    }

     //El viewModelScope.launch se establece cuando en la misma vista se van a realizar operaciones estando este view activo

    fun insertar(usuario: Usuario) = viewModelScope.launch {
        val nuevoIdFila = usuarioDatos.insertar(usuario)
        if (nuevoIdFila > -1){
            //Toast
        }else{
           //Toast
        }

    }
    fun actualizar(usuario: Usuario) = viewModelScope.launch {
        val numDeila = usuarioDatos.actualizar(usuario)
        if (numDeila > 0) {
            nameUsuario.value = null
            emaiUsuario.value = null
            actualizadoOBorrado = false
            guardarOActualizarBtn.value = "Guardar"

           //Mensaje de Borrado
        } else {
            //mensajeEstado.value = Eventos("Error")
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