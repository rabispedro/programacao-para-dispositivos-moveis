package com.example.a0xc0ffee.model.mapper

import com.example.a0xc0ffee.model.Relatorio

object RelatorioMapper: Mapper<Relatorio> {
    override fun toMap(model: Relatorio): Map<String, Any> {
        return mapOf(
            "id" to model.id,
            "cpf" to model.cpf.value
        )
    }

    override fun fromMap(map: Map<String, Any>): Relatorio {
        return Relatorio(
            map["id"] as String,
            map["cpf"] as String,
        )
    }
}
