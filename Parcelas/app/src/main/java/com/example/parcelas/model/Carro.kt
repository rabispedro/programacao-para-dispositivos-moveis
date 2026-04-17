package com.example.parcelas.model

data class Carro(
    var modelo: String,
    var ano: Int,
    var potencia: Float,
    var fabricante: String,
    var temQuatroPortas: Boolean
): Automovel(modelo, ano, potencia, fabricante)
