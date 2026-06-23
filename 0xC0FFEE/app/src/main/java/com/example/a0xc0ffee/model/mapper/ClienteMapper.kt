package com.example.a0xc0ffee.model.mapper

import com.example.a0xc0ffee.model.Cliente

object ClienteMapper: Mapper<Cliente> {
    override fun toMap(model: Cliente): Map<String, Any> {
        return mapOf(
            "cpf" to model.cpf.value,
            "nome" to model.nome.value,
            "telefone" to model.telefone.value,
            "endereco" to model.endereco.value,
            "instagram" to model.instagram.value
        )
    }

    override fun fromMap(map: Map<String, Any>): Cliente {
        return Cliente(
            map["cpf"] as String,
            map["nome"] as String,
            map["telefone"] as String,
            map["endereco"] as String,
            map["instagram"] as String
        )
    }
}
