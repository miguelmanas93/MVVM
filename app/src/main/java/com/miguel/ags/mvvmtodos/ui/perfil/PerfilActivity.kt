package com.miguel.ags.mvvmtodos.ui.perfil

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.miguel.ags.mvvmtodos.R
import com.miguel.ags.mvvmtodos.databinding.ActivityPerfilBinding
import com.miguel.ags.mvvmtodos.model.Usuario
import com.miguel.ags.mvvmtodos.model.UsuarioDatos
import com.miguel.ags.mvvmtodos.model.UsuarioViewModelFactory
import com.miguel.ags.mvvmtodos.model.database.AppDatabase

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    private lateinit var perfilViewModel: PerfilViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_perfil)

        //Se crea la instancia de la base de datos
        val dao = AppDatabase.getInstance(application).usuarioDao
        //metodos de la bd
        val repository = UsuarioDatos(dao)
        val factory =
            UsuarioViewModelFactory(
                repository
            )

        perfilViewModel = ViewModelProvider(this, factory).get(PerfilViewModel::class.java)
        binding.datosUsuario = perfilViewModel
        binding.lifecycleOwner = this
        displayUsuario()

        perfilViewModel.mensaje.observe(this, Observer {
            it.getContentSiNoManipulado()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun listItemClicked(usuario: Usuario) {
        //Toast.makeText(this,"selected name is ${subscriber.name}",Toast.LENGTH_LONG).show()
        perfilViewModel.iniciarActualizaroBorrar(usuario)
    }

    private fun initDatos() {

        displayUsuario()
    }


    private fun displayUsuario() {
        perfilViewModel.usuario.observe(this, Observer {
            Log.i("MYTAG", it.toString())
            if (it.isNotEmpty()) {
                perfilViewModel.nameUsuario.value = it.get(0).name
                perfilViewModel.passUsuario.value = it.get(0).password
                perfilViewModel.emaiUsuario.value = it.get(0).email
                perfilViewModel.idUsuario.value = it.get(0).id.toString()
            }
            //  adapter.setList(it)
            // adapter.notifyDataSetChanged()
        })
    }

}

