package com.example.a0xc0ffee.model

import com.example.a0xc0ffee.model.type.PontoDaTorra
import com.example.a0xc0ffee.model.type.TipoDoGrao

data class Produto(
    val id: String,
    val tipoDoGrao: TipoDoGrao,
    val pontoDaTorra: PontoDaTorra,
    val valor: Double,
    val blend: Boolean
) {
    constructor(id: String, tipoDoGrao: String, pontoDaTorra: String, valor: String, blend: String) :
            this(id, TipoDoGrao.valueOf(tipoDoGrao), PontoDaTorra.valueOf(pontoDaTorra), valor.toDouble(), blend.toBoolean())
}