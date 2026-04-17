package com.example.automoveis.viewmodel

import android.util.Log
import com.example.automoveis.IObservable
import com.example.automoveis.IObserver
import com.example.automoveis.model.Carro

class CarroViewModel(override val observers: MutableList<IObserver>) : IObservable {
    private val frota: MutableList<Carro> = ArrayList();

    fun cria(carro: Carro): Boolean {
        val result = frota.add(carro)
        onChange()
        return result
    }

    fun listaTodos(): Set<Carro> {
        return frota.toSet()
    }

    fun atualiza(carro: Carro): Boolean {
        val carroEncontrado = frota.firstOrNull({
            it.modelo == carro.modelo
        })

        if (carroEncontrado == null) {
            return false
        }

        carroEncontrado.modelo = carro.modelo
        carroEncontrado.fabricante = carro.fabricante
        carroEncontrado.potencia = carro.potencia
        carroEncontrado.ano = carro.ano
        carroEncontrado.temQuatroPortas = carro.temQuatroPortas

        onChange()

        return true
    }

    fun remove(carro: Carro): Boolean {
        val result = frota.remove(carro)
        Log.d("debug", "Carro removido? $result")
        onChange()
        return result
    }
}
