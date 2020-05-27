package com.miguel.ags.mvvmtodos.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miguel.ags.mvvmtodos.model.Todos
import com.miguel.ags.mvvmtodos.model.TodosDao
import com.miguel.ags.mvvmtodos.model.Usuario
import com.miguel.ags.mvvmtodos.model.UsuarioDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Database(
    entities = [
        Todos::class,
        Usuario::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todosDao(): TodosDao

    abstract val usuarioDao: UsuarioDao

    //Instancia la base de datos en ROOM
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "usuario"
                    ).build()
                }
                return instance
            }
        }

    }

}

class RetrofitInstance {
    companion object {
        private const val BASE_URL: String = "https://postman-echo.com"

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