package com.example.automoveis.viewmodel

import android.util.Log
import com.example.automoveis.IObservable
import com.example.automoveis.IObserver
import com.example.automoveis.model.Onibus

class OnibusViewModel(override val observers: MutableList<IObserver>) : IObservable {
    private val frota: MutableList<Onibus> = ArrayList();

    fun cria(onibus: Onibus): Boolean {
        val result = frota.add(onibus)
        onChange()
        return result
    }

    fun listaTodos(): Set<Onibus> {
        return frota.toSet()
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

        onChange()

        return true
    }

    fun remove(onibus: Onibus): Boolean {
        val result = frota.remove(onibus)
        Log.d("debug", "Ônibus removido? $result")
        onChange()
        return result
    }
}
