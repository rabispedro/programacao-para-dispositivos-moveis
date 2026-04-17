package com.example.parcelas.model

data class Caminhao(
    var modelo: String,
    var ano: Int,
    var potencia: Float,
    var fabricante: String,
    var pesoSuportado: Float
) : Automovel(modelo, ano, potencia, fabricante)