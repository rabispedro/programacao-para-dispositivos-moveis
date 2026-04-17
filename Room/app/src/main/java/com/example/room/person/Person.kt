package com.example.room.person

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Person (
    @PrimaryKey
    val cpf: String,
    val name: String)