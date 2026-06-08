package com.example.a0xc0ffee.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.a0xc0ffee.controller.ProdutoController
import com.example.a0xc0ffee.model.Produto
import com.example.a0xc0ffee.model.type.PontoDaTorra
import com.example.a0xc0ffee.model.type.TipoDoGrao
import com.example.a0xc0ffee.ui.theme.icon.LocalCoffeeIcon
import java.util.UUID

class ProdutoView(val controller: ProdutoController): View {
    override val displayName = "Produto"
    override val displayIcon = LocalCoffeeIcon
    override val tintColor = Color(0xFF8B4513)

    @Composable
    override fun View() {
        val produtos = remember { SnapshotStateList<Produto>() }

        var estadoCriarProduto by remember { mutableStateOf(false) }
        var estadoBuscarProduto by remember { mutableStateOf(false) }

        var idProduto by remember { mutableStateOf("") }
        var tipoDoGraoProduto by remember { mutableStateOf(TipoDoGrao.ARABICA) }
        var pontoDaTorraProduto by remember { mutableStateOf(PontoDaTorra.MEDIA) }
        var valorProduto by remember { mutableDoubleStateOf(0.0) }
        var blendProduto by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            produtos.clear()
            produtos.addAll(controller.listar())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(Color.Magenta),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Ações")

            Button(onClick = { estadoCriarProduto = true }) {
                Text("Novo Produto")
            }

            Button(onClick = { estadoBuscarProduto = true }) {
                Text("Buscar Produto")
            }

            if (estadoCriarProduto) {
                AlertDialog(
                    onDismissRequest = {  estadoCriarProduto = false},
                    title = { Text("Novo Produto") },
                    text = { Text("Insira os dados para o cadastro do novo produto") },
                    confirmButton = {
                        Button(onClick = {
                            estadoCriarProduto = false
                            idProduto = UUID.randomUUID().toString()
                            val produto = Produto(idProduto, tipoDoGraoProduto, pontoDaTorraProduto, valorProduto, blendProduto)
                            controller.cadastrar(produto)
                        }) {
                            Text("Criar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            estadoCriarProduto = false
                            idProduto = ""
                            tipoDoGraoProduto = TipoDoGrao.ARABICA
                            pontoDaTorraProduto = PontoDaTorra.MEDIA
                            valorProduto = 0.0
                            blendProduto = false
                        }) {
                            Text("Cancelar")
                        }
                    }
                )
            }

            if (estadoBuscarProduto) {
                AlertDialog(
                    onDismissRequest = {  estadoBuscarProduto = false},
                    title = { Text("Buscar Produto") },
                    text = { Text("Insira os dados para a busca do produto") },
                    confirmButton = {
                        Button(onClick = {
                            estadoBuscarProduto = false
                            idProduto = UUID.randomUUID().toString()
                            val produto = Produto(idProduto, tipoDoGraoProduto, pontoDaTorraProduto, valorProduto, blendProduto)
                            controller.cadastrar(produto)
                        }) {
                            Text("Buscar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            estadoBuscarProduto = false
                            idProduto = ""
                            tipoDoGraoProduto = TipoDoGrao.ARABICA
                            pontoDaTorraProduto = PontoDaTorra.MEDIA
                            valorProduto = 0.0
                            blendProduto = false
                        }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}