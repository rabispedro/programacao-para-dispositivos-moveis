package com.example.prova2.model

import com.example.prova2.model.type.AromaQueijo
import com.example.prova2.model.type.FormatoRegularQueijo
import com.example.prova2.model.type.SaborQueijo
import com.example.prova2.model.type.TipoQueijo
import com.example.prova2.model.vo.Nome
import com.example.prova2.model.vo.Preco

data class Queijo(
    val id: String,
    val nome: Nome,
    val tipo: TipoQueijo,
    val aroma: AromaQueijo,
    val formatoRegular: FormatoRegularQueijo,
    val sabor: SaborQueijo,
    val preco: Preco,
    val loja: Loja
) {
    constructor(
        id: String,
        nome: String,
        tipo: String,
        aroma: String,
        formatoRegular: String,
        sabor: String,
        preco: String,
        loja: Loja
    ) :
        this(
            id,
            Nome(nome),
            TipoQueijo.valueOf(tipo),
            AromaQueijo.valueOf(aroma),
            FormatoRegularQueijo.valueOf(formatoRegular),
            SaborQueijo.valueOf(sabor),
            Preco(preco.toDouble()),
            loja
        )
}