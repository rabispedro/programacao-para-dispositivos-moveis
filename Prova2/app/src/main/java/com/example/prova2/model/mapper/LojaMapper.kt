package com.example.prova2.model.mapper

import com.example.prova2.model.Loja

object LojaMapper : Mapper<Loja> {
    override fun toMap(model: Loja): Map<String, Any> {
        return mapOf(
            "id" to model.id,
            "nome" to model.nome.value,
            "quantidadeFuncionario" to model.quantidadeFuncionario.value.toString(),
            "producaoDiaria" to model.producaoDiaria.value.toString()
        )
    }

    override fun fromMap(map: Map<String, Any>): Loja {
        return Loja(
            map["id"] as String,
            map["nome"] as String,
            map["quantidadeFuncionario"] as String,
            map["producaoDiaria"] as String
        )
    }
}