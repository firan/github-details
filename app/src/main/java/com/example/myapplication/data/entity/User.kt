package com.example.myapplication.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User(
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "identifier") val identifier: String?,
    @ColumnInfo(name = "created_at") val createdAt: Date
){
    @PrimaryKey(autoGenerate = true) var uid: Int? = null
}
