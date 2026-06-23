package com.example.a0xc0ffee.model.mapper

import com.example.a0xc0ffee.model.Produto

object ProdutoMapper: Mapper<Produto> {
    override fun toMap(model: Produto): Map<String, Any> {
        return mapOf(
            "id" to model.id,
            "tipoDoGrao" to model.tipoDoGrao.name,
            "pontoDaTorra" to model.pontoDaTorra.name,
            "valor" to model.valor.toString(),
            "blend" to model.blend.toString()
        )
    }

    override fun fromMap(map: Map<String, Any>): Produto {
        return Produto(
            map["id"] as String,
            map["tipoDoGrao"] as String,
            map["pontoDaTorra"] as String,
            map["valor"] as String,
            map["blend"] as String
        )
    }
}
