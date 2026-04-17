package com.example.automoveis.model

abstract class Automovel(
    open var modelo: String,
    open var ano: Int,
    open var potencia: Float,
    open var fabricante: String
) {
    override fun toString(): String {
        return "$modelo | $ano | $potencia (CV) | $fabricante"
    }

    override fun hashCode(): Int {
        var result = ano
        result = 31 * result + potencia.hashCode()
        result = 31 * result + modelo.hashCode()
        result = 31 * result + fabricante.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Automovel

        if (ano != other.ano) return false
        if (potencia != other.potencia) return false
        if (modelo != other.modelo) return false
        if (fabricante != other.fabricante) return false

        return true
    }
}
