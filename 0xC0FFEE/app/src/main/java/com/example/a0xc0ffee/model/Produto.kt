package com.example.a0xc0ffee.model

import com.example.a0xc0ffee.model.type.PontoDaTorra
import com.example.a0xc0ffee.model.type.TipoDoGrao

data class Produto(
    val id: String,
    val tipoDoGrao: TipoDoGrao,
    val pontoDaTorra: PontoDaTorra,
    val valor: Double,
    val blend: Boolean
) {}