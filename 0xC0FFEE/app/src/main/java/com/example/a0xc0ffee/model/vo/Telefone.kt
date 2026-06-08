package com.example.a0xc0ffee.model.vo

data class Telefone(val value: String): ValueObject {
    init {
        if (!isValid()){
            throw IllegalArgumentException("Telefone is not valid")
        }
    }

    override fun isValid(): Boolean {
        return Regex("""(\+\d{13})|(\+\d{2}\ \d{2}\ \d{1}\ \d{4}-\d{4})|(\d{11})""").matches(value)
    }
}