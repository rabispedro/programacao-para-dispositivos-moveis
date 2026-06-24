package com.example.prova2.model.vo

data class Preco(val value: Double) : ValueObject {
    private val MAX = 1000000.00
    private val MIN = 1.00

    init {
        if (!isValid()){
            throw IllegalArgumentException("Preco is not valid")
        }
    }

    override fun isValid(): Boolean {
        return !value.isNaN()
                && value.isFinite()
                && value > MIN
                && value < MAX
    }
}
