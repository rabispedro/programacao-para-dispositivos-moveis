package com.example.prova2.model.vo

data class ProducaoDiaria(val value: Int) : ValueObject {
    private val MAX = 1000
    private val MIN = 0

    init {
        if (!isValid()){
            throw IllegalArgumentException("Producao Diaria is not valid")
        }
    }

    override fun isValid(): Boolean {
        return value in (MIN + 1)..< MAX
    }
}
