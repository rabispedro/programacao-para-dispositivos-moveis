package com.example.a0xc0ffee.controller

import android.util.Log
import com.example.a0xc0ffee.model.Cliente

class ClienteController: BaseController("Cliente") {
    fun cadastrar(cliente: Cliente): Boolean {
        var result = false
        repository
                .collection(collection)
                .add(cliente)
                .addOnSuccessListener {
                    result = true
                }

        return result
    }

    fun listar(): List<Cliente> {
        val result: MutableList<Cliente> = mutableListOf()

        repository
            .collection(collection)
            .get()
            .addOnSuccessListener { row ->
                for (obj in row) {
                    Log.d("debug", "Object: ${obj.data}")
//                    val objeto = cliente.toObject(Cliente::class.java)
                    val cliente = Cliente(obj.data)
                    Log.d("debug", "Document: $cliente")
                    result.add(cliente)
                }
            }

        return result
    }

    fun buscar(cpf: String): Cliente {
//        val cliente = repository.find { it.cpf.equals(cpf) }
//
//        if cliente != null {
//            return cliente
//        }

        throw Exception("Cliente não encontrado")
    }

    fun deletar(cpf: String): Boolean {
//        val cliente = repository.find { it.cpf.equals(cpf) }
//
//        if cliente != null {
//            return false
//        }
//
//        repository.remove(cliente)
        return true
    }

    fun alterar(cliente: Cliente): Boolean {
//        var clienteEncontrado = repository.find { it.cpf.equals(cliente.cpf) }
//
//        if clienteEncontrado != null {
//            return false
//        }
//
//        clienteEncontrado = cliente

        return true
    }
}