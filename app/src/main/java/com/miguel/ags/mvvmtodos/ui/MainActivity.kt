package com.miguel.ags.mvvmtodos.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.miguel.ags.mvvmtodos.R
import com.miguel.ags.mvvmtodos.databinding.ActivityMainBinding
import com.miguel.ags.mvvmtodos.model.UsuarioDatos
import com.miguel.ags.mvvmtodos.model.UsuarioViewModelFactory
import com.miguel.ags.mvvmtodos.model.database.AppDatabase
import com.miguel.ags.mvvmtodos.ui.perfil.PerfilViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var perfilViewModel: PerfilViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            enviarMail()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        //Instanciamos la BBDD
        cargarDatos()

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_perfil
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun cargarDatos() {
        //Se crea la instancia de la base de datos
        val dao = AppDatabase.getInstance(application).usuarioDao
        //metodos de la bd
        val repository = UsuarioDatos(dao)
        val factory =
            UsuarioViewModelFactory(
                repository
            )

        perfilViewModel = ViewModelProvider(this, factory).get(PerfilViewModel::class.java)
        binding.lifecycleOwner = this
        displayUsuario()

        perfilViewModel.mensaje.observe(this, Observer {
            it.getContentSiNoManipulado()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayUsuario() {
              //  var email = findViewById<TextView>(R.id.tvEmailPersona)
        perfilViewModel.usuario.observe(this, Observer {
            Log.i("MYTAG", it.toString())
            if (it.isNotEmpty()) {

                findViewById<TextView>(R.id.tvNombrePersona).text = it.get(0).name
                findViewById<TextView>(R.id.tvEmailPersona).text = it.get(0).email
            } else {
                findViewById<TextView>(R.id.tvNombrePersona).text = "Complete sus datos en Editar Perfil"
                findViewById<TextView>(R.id.tvEmailPersona).text = "email desconocido"
            }
        })
    }

    fun enviarMail(){
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, "test@gmail.com")
            putExtra(Intent.EXTRA_SUBJECT, "subtitulo")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
