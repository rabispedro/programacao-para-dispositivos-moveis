package com.example.prova2.model.mapper

interface Mapper<T> {
    fun toMap(model: T): Map<String, Any>
    fun fromMap(map: Map<String, Any>): T
}