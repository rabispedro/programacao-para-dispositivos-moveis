package com.example.parcelas.viewmodel

import com.example.parcelas.model.Carro
import com.example.parcelas.view.StepView

class CarroViewModel {
    private val carros: MutableList<Carro> = ArrayList();

    fun cria(carro: Carro): Boolean {
        return carros.add(carro)
    }

    fun listaTodos(): List<Carro> {
        return carros.toList()
    }

    fun atualiza(carro: Carro): Boolean {
        val carroEncontrado = carros.firstOrNull({
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

        return true
    }

    fun remove(carro: Carro): Boolean {
        return carros.remove(carro)
    }
}