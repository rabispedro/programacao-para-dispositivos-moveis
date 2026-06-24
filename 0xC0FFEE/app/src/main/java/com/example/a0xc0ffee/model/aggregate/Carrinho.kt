package com.example.a0xc0ffee.model.aggregate

import android.util.Log
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.Produto

// NOTE: Carrinho simplificado em memória: Singleton com Map<Cliente, Set<Pair<Produto, Int>>> e limite de 200 unidades de Produto por Cliente
// "11122233344" -> [ {{"aaa", "CONILON", "MEDIA", 15.02, true }, 5}, {{"bbb", "GEISHA", "QUEIMADA", 11.99, false }, 100} ]
// "99988877766" -> [ {{"ccc", "", "MUITO_CLARA", 5.02, false }, 1} ]
object Carrinho {
    private const val MAXIMO: Int = 200
    private val carrinho = HashMap<Cliente, HashSet<Pair<Produto, Int>>>()

    fun limparCarrinho() {
        carrinho.clear()
    }

    fun limparCarrinho(cliente: Cliente) {
        carrinho[cliente]?.clear()
    }

    fun temItems(cliente: Cliente): Boolean {
        return carrinho[cliente]?.isNotEmpty() ?: false
    }

    fun temItems(): Boolean {
        if (carrinho.isNotEmpty()) {
            return carrinho.keys.any {
                carrinho[it]!!.isNotEmpty()
            }
        }

        return false
    }

    fun mostrarItems(cliente: Cliente): HashSet<Pair<Produto, Int>> {
        Log.d("debug", "Mostrando items de ${cliente.nome.value}: ${carrinho[cliente]!!.size}")
        return carrinho[cliente]!!
    }

    fun mostrarClientes(): List<Cliente> {
        return carrinho.keys.toMutableList()
    }

    fun adicionarItem(cliente: Cliente, produto: Produto, quantidade: Int = 1) {
        Log.d("debug", "Adicionar item ao carrinho: $cliente, $produto, $quantidade")

        if (quantidade <= 0) {
            Log.d("debug", "Quantidade inválida")
            return
        }

        if (carrinho[cliente] == null) {
            Log.d("debug", "Cliente sem pedidos no carrinho ainda")
            carrinho[cliente] = LinkedHashSet<Pair<Produto, Int>>()
        }

        var item = carrinho[cliente]?.find { it.first == produto }
        Log.d("debug", "item do carrinho: $item")
        if (item == null) {
            Log.d("debug", "Cliente não tem o produto no carrinho")
            item = Pair(produto, quantidade)
        }

        if (item.second > MAXIMO) {
            item = Pair(produto, MAXIMO)
            Log.d("debug", "Quantidade máxima do produto excedida")
        }

        carrinho[cliente]?.add(item)
    }

    fun removerItem(cliente: Cliente, produto: Produto, quantidade: Int = 1) {
        if (quantidade <= 0) {
            Log.d("debug", "Quantidade inválida")
            return
        }

        if (carrinho[cliente] == null) {
            Log.d("debug", "Cliente não possui items")
            return
        }

        var item = carrinho[cliente]?.find { it.first == produto }
        if (item == null) {
            Log.d("debug", "Cliente não possui este item")
            return
        }

        item = Pair(item.first, (item.second - quantidade))

        if (item.second <= 0) {
            carrinho[cliente]?.remove(item)
            Log.d("debug", "Quantidade mínima do produto excedida")
        }
    }
}