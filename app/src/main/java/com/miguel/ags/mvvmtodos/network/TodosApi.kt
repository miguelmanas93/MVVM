package com.miguel.ags.mvvmtodos.network


import com.miguel.ags.mvvmtodos.model.Todos
import com.miguel.ags.mvvmtodos.model.Usuario
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * The interface which provides methods to get result of webservices
 */
interface TodosApi {
    /**
     * Get the list of the pots from the API
     */
    @GET("/todos")
    fun getTodos(): Observable<List<Todos>>

    /**
     * Get the list of the pots from the API
     */
    @GET("/usuario")
    fun getTodosUsuarios(): Observable<List<Usuario>>

    // Peticion Iniciar Sesion
    @Headers("Content-Type:application/json")
    @POST("post")
    fun iniciarSesion(
        @Body info: Usuario
    ): retrofit2.Call<Usuario>
}

