package com.example.automoveis.model

data class Carro(
    override var modelo: String,
    override var ano: Int,
    override var potencia: Float,
    override var fabricante: String,
    var temQuatroPortas: Boolean
): Automovel(modelo, ano, potencia, fabricante) {
    override fun toString(): String {
        return super.toString() + " | ${if(temQuatroPortas) "4" else "2"} portas"
    }
}
