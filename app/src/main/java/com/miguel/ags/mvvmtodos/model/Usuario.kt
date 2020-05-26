package com.miguel.ags.mvvmtodos.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity (tableName = "usuario")
data class Usuario(
    @field:PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var email: String,
    val password: String,
    val token: String)


