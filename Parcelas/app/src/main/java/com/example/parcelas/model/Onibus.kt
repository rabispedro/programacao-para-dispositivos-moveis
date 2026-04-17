package com.example.parcelas.model

data class Onibus(
    var modelo: String,
    var ano: Int,
    var potencia: Float,
    var fabricante: String,
    var quantidadeLugares: Int
) : Automovel(modelo, ano, potencia, fabricante)