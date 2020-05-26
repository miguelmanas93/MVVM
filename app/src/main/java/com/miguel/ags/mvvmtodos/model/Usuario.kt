package com.miguel.ags.mvvmtodos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "usuario")
data class Usuario(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_usuario")
    val id: Int,
    @ColumnInfo(name = "nombre_usuario")
    var name: String,
    @ColumnInfo(name = "email_usuario")
    var email: String,
    @ColumnInfo(name = "pass_usuario")
    val password: String,
    @ColumnInfo(name = "token_usuario")
    val token: String)


