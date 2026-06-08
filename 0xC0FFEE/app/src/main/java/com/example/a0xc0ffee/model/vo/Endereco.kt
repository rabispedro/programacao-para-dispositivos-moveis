package com.example.a0xc0ffee.model.vo

data class Endereco(val value: String): ValueObject {
    init {
        if (!isValid()){
            throw IllegalArgumentException("Endereco is not valid")
        }
    }

    override fun isValid(): Boolean {
        return !value.isEmpty() && value.length <= 100
    }
}