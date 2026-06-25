package com.example.prova2.model.mapper

import com.example.prova2.model.Queijo

object QueijoMapper : Mapper<Queijo> {
    override fun toMap(model: Queijo): Map<String, Any> {
        return mapOf(
            "id" to model.id,
            "nome" to model.nome.value,
            "tipo" to model.tipo.name,
            "aroma" to model.aroma.name,
            "aparenciaAgradavel" to model.aparenciaAgradavel.name,
            "formatoRegular" to model.formatoRegular.name,
            "sabor" to model.sabor.name,
            "preco" to model.preco.value.toString(),
            "loja" to LojaMapper.toMap(model.loja)
        )
    }

    override fun fromMap(map: Map<String, Any>): Queijo {
        return Queijo(
            map["id"] as String,
            map["nome"] as String,
            map["tipo"] as String,
            map["aroma"] as String,
            map["aparenciaAgradavel"] as String,
            map["formatoRegular"] as String,
            map["sabor"] as String,
            map["preco"] as String,
            LojaMapper.fromMap(map["loja"] as Map<String, Any>)
        )
    }
}