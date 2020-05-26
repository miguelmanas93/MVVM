package com.miguel.ags.mvvmtodos.network


import com.miguel.ags.mvvmtodos.model.*
import com.miguel.ags.mvvmtodos.model.Todos
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

class RetrofitInstance {
    companion object {
        val BASE_URL: String = "https://postman-echo.com"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        /**
         * Companion object to create the Mercatus
         */
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}