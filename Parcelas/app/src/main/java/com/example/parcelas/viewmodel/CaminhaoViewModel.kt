package com.example.parcelas.viewmodel

import com.example.parcelas.model.Caminhao
import com.example.parcelas.view.StepView

class CaminhaoViewModel {
    private val caminhoes: MutableList<Caminhao> = ArrayList();

    fun cria(caminhao: Caminhao): Boolean {
        return caminhoes.add(caminhao)
    }

    fun listaTodos(): List<Caminhao> {
        return caminhoes.toList()
    }

    fun atualiza(caminhao: Caminhao): Boolean {
        val caminhaoEncontrado = caminhoes.firstOrNull({
            it.modelo == caminhao.modelo
        })

        if (caminhaoEncontrado == null) {
            return false
        }

        caminhaoEncontrado.modelo = caminhao.modelo
        caminhaoEncontrado.fabricante = caminhao.fabricante
        caminhaoEncontrado.potencia = caminhao.potencia
        caminhaoEncontrado.ano = caminhao.ano
        caminhaoEncontrado.pesoSuportado = caminhao.pesoSuportado

        return true
    }

    fun remove(caminhao: Caminhao): Boolean {
        return caminhoes.remove(caminhao)
    }
}