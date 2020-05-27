package com.miguel.ags.mvvmtodos.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.miguel.ags.mvvmtodos.R
import com.miguel.ags.mvvmtodos.model.Usuario
import com.miguel.ags.mvvmtodos.model.database.RetrofitInstance
import com.miguel.ags.mvvmtodos.network.TodosApi
import com.miguel.ags.mvvmtodos.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var etEmailUsuario: EditText? = null
    private var etContrasenia: EditText? = null
    private var btLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Cambiamos el nombre a la barra superior
        val actionBar = supportActionBar
        actionBar!!.title = "AGS - Login test"

        //Declaramos los elementos que vamos a tener en la pantalla
        etEmailUsuario = findViewById(R.id.etEmailUsuario)
        etContrasenia = findViewById(R.id.etContrasenia)
        btLogin = findViewById<Button>(R.id.btLogin)

        val backgroundimage = findViewById(R.id.background) as View
        backgroundimage.background.alpha = 80

        btLogin!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btLogin -> {
                val email = etEmailUsuario?.text.toString()
                val contrasenia = etContrasenia?.text.toString()
                comprobar(email, contrasenia)
            }
            R.id.tvRecordarContrasenia -> {
            }
        }
    }

    private fun comprobar(email: String, contrasenia: String) {
        if (validarFormulario(email, contrasenia)) {
            val sharedPreferences = getSharedPreferences("TOKEN_INFO", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            iniciarSesion(email, contrasenia, editor);
        }
    }

    private fun validarFormulario(email: String, contrasenia: String): Boolean {
        var isValid = true
        if (!email.esValido()) {
            Toast.makeText(this, "Error en el mail", Toast.LENGTH_LONG).show()
            isValid = false
        }
        if (contrasenia.isEmpty()) {
            Toast.makeText(this, "La contraseña no puede estar vacia", Toast.LENGTH_LONG).show()
            isValid = false
        } else {
            isValid = true
        }
        return isValid
    }

    private fun String.esValido(): Boolean =
        this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun iniciarSesion(email: String, pass: String, editor: SharedPreferences.Editor) {
        val purApp = RetrofitInstance.getRetrofitInstance().create(TodosApi::class.java)
        val signInInfo = Usuario(0, "", email, pass, "")

        purApp.iniciarSesion(signInInfo).enqueue(object : Callback<Usuario> {
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                if (t.cause is ConnectException) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Check your connection!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Something bad happened!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(
                call: Call<Usuario>,
                response: Response<Usuario>
            ) {
                if (response.code() == 200) {
                    editor.putString("token", response.body()?.token)
                    editor.putString("user_id", response.body()?.id.toString())
                    editor.commit()

                    Toast.makeText(this@LoginActivity, "Login success!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else if (response.code() == 500) {
                    Toast.makeText(
                        this@LoginActivity,
                        "The given email or password is wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}


