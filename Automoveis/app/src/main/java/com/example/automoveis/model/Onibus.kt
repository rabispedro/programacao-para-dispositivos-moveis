package com.example.automoveis.model

data class Onibus(
    override var modelo: String,
    override var ano: Int,
    override var potencia: Float,
    override var fabricante: String,
    var quantidadeLugares: Int
) : Automovel(modelo, ano, potencia, fabricante) {
    override fun toString(): String {
        return super.toString() + " | $quantidadeLugares Lugares"
    }
}
