package com.example.parcelas.viewmodel

import com.example.parcelas.model.Onibus

class OnibusViewModel {
    private val frota: MutableList<Onibus> = ArrayList();

    fun cria(onibus: Onibus): Boolean {
        return frota.add(onibus)
    }

    fun listaTodos(): List<Onibus> {
        return frota.toList()
    }

    fun atualiza(onibus: Onibus): Boolean {
        val onibusEncontrado = frota.firstOrNull({
            it.modelo == onibus.modelo
        })

        if (onibusEncontrado == null) {
            return false
        }

        onibusEncontrado.modelo = onibus.modelo
        onibusEncontrado.fabricante = onibus.fabricante
        onibusEncontrado.potencia = onibus.potencia
        onibusEncontrado.ano = onibus.ano
        onibusEncontrado.quantidadeLugares = onibus.quantidadeLugares

        return true
    }

    fun remove(onibus: Onibus): Boolean {
        return frota.remove(onibus)
    }
}