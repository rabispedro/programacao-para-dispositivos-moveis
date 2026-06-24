package com.example.prova2.model.vo

data class QuantidadeFuncionario(val value: Int) : ValueObject {
    private val MAX = 250
    private val MIN = 0

    init {
        if (!isValid()){
            throw IllegalArgumentException("Quantidade Funcionario is not valid")
        }
    }

    override fun isValid(): Boolean {
        return value in (MIN + 1)..< MAX
    }
}
