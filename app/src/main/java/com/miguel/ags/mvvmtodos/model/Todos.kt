package com.miguel.ags.mvvmtodos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Todos(
    @ColumnInfo(name = "id_user")
    val userId: Int,
    @ColumnInfo(name = "id_entrada")
    @field:PrimaryKey
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "status")
    val completed: Boolean
)
