package com.example.a0xc0ffee.model.mapper

interface Mapper<T> {
    fun toMap(model: T): Map<String, Any>
    fun fromMap(map: Map<String, Any>): T
}
