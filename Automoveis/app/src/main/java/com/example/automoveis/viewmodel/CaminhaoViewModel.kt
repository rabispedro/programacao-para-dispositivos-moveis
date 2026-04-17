package com.example.automoveis.viewmodel

import android.util.Log
import com.example.automoveis.IObservable
import com.example.automoveis.IObserver
import com.example.automoveis.model.Caminhao

class CaminhaoViewModel(override val observers: MutableList<IObserver>) : IObservable {
    private val frota: MutableList<Caminhao> = ArrayList();

    fun cria(caminhao: Caminhao): Boolean {
        val result = frota.add(caminhao)
        onChange()
        return result
    }

    fun listaTodos(): Set<Caminhao> {
        return frota.toSet()
    }

    fun atualiza(caminhao: Caminhao): Boolean {
        val caminhaoEncontrado = frota.firstOrNull({
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

        onChange()

        return true
    }

    fun remove(caminhao: Caminhao): Boolean {
        val result = frota.remove(caminhao)
        Log.d("debug", "Caminhão removido? $result")
        onChange()
        return result
    }
}
