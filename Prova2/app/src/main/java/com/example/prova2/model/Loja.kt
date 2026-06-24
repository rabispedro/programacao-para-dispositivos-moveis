package com.example.prova2.model

import com.example.prova2.model.vo.Nome
import com.example.prova2.model.vo.ProducaoDiaria
import com.example.prova2.model.vo.QuantidadeFuncionario

data class Loja(
    val id: String,
    val nome: Nome,
    val quantidadeFuncionario: QuantidadeFuncionario,
    val producaoDiaria: ProducaoDiaria
) {
    constructor(
        id: String,
        nome: String,
        quantidadeFuncionario: String,
        producaoDiaria: String
    ) :
        this(
            id,
            Nome(nome),
            QuantidadeFuncionario(quantidadeFuncionario.toInt()),
            ProducaoDiaria(producaoDiaria.toInt())
        )
}
