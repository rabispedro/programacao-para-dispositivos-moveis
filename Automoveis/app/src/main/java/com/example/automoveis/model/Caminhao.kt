package com.example.automoveis.model

data class Caminhao(
    override var modelo: String,
    override var ano: Int,
    override var potencia: Float,
    override var fabricante: String,
    var pesoSuportado: Float
) : Automovel(modelo, ano, potencia, fabricante) {
    override fun toString(): String {
        return super.toString() + " | $pesoSuportado (Kg)"
    }
}
