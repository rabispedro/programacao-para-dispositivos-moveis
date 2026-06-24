package com.example.a0xc0ffee.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a0xc0ffee.controller.PedidoController
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.Pedido
import com.example.a0xc0ffee.model.aggregate.Carrinho
import com.example.a0xc0ffee.ui.theme.icon.ReceiptIcon
import com.example.a0xc0ffee.ui.theme.icon.SearchIcon
import com.example.a0xc0ffee.ui.theme.icon.ShoppingCartIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class PedidoView(val controller: PedidoController): View {
    override val displayName = "Pedido"
    override val displayIcon = ReceiptIcon
    override val tintColor = Color(0xFF8B4513)

    private val TAMANHO_TEXTO_PARA_BUSCAR = 11

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun View(scope: CoroutineScope, snackbar: SnackbarHostState) {
        val estadoPedidos = rememberLazyListState()
        val pedidos = remember { mutableStateListOf<Pedido>() }

        var estadoCriarPedido by remember { mutableStateOf(false) }

        var estadoBuscarPedido by remember { mutableStateOf(false) }
        var textoBuscaPedido by remember { mutableStateOf("") }

        LaunchedEffect(scope) {
            pedidos.clear()
            val entities = controller.listar()
            pedidos.addAll(entities)
            Log.d("debug", "Pedidos: ${pedidos.size}")
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(bottom = 30.dp))

            Text(text = "Pedidos",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth().padding(8.dp))

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            TextField(value = textoBuscaPedido,
                onValueChange = {
                    textoBuscaPedido = it.trim()
                    estadoBuscarPedido = (textoBuscaPedido.length >= TAMANHO_TEXTO_PARA_BUSCAR)
                },
                leadingIcon = { Icon(SearchIcon, "Lupa") },
                placeholder = { Text("O pedido que eu quero é o...") },
                modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp))

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            LazyColumn(state = estadoPedidos,
                modifier = Modifier.fillMaxWidth().heightIn(480.dp, 500.dp).background(Color.LightGray),
                content = {
                    items(pedidos) {
                        Row (horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp).heightIn(40.dp)) {

                            Log.d("debug", "Item: $it")
                            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                Text(text = it.cliente.nome.value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(text = it.cliente.cpf.safeShow(), fontWeight = FontWeight.Bold)
                                Text(it.cliente.instagram.value, fontWeight = FontWeight.Light)

                                HorizontalDivider(thickness = 1.dp)

                                Column(modifier = Modifier.padding(8.dp)) {
                                    it.items.forEach { item ->
                                        Text(text = item.first.tipoDoGrao.localize(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                        Text(text = item.first.pontoDaTorra.localize(), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        Text("R$: ${item.first.valor} (${item.second} unidade${ if(item.second > 1) "s" else "" })", fontSize = 10.sp,)
                                        Text(text = if (item.first.blend) "Blendelicioso" else "Desblendado", fontSize = 10.sp, fontWeight = FontWeight.Light)
                                        Text("Total: R$${item.first.valor * item.second}", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        HorizontalDivider(thickness = 1.dp)
                                    }
                                }

                                Text("Adquirido em: ${it.data}")
                                Text("Total da compra: R$${it.getTotal()}", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 10.dp))

            if (Carrinho.temItems()) {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp), horizontalArrangement = Arrangement.End) {
                    Button(onClick = { estadoCriarPedido = true }, modifier = Modifier.padding(vertical = 4.dp)) {
                        Icon(ShoppingCartIcon, "Adicionar")
                    }
                }
            }

            when {
                estadoCriarPedido -> {
                    estadoCriarPedido = onCriarPedido(scope, snackbar)
                }
                estadoBuscarPedido -> {
                    LaunchedEffect(scope) {
                        pedidos.clear()
                        val entities = controller.listar(textoBuscaPedido)
                        pedidos.addAll(entities)
                        Log.d("debug", "Pedidos: ${pedidos.size}")
                        estadoBuscarPedido = false
                        textoBuscaPedido = ""
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun onCriarPedido(scope: CoroutineScope, snackbar: SnackbarHostState): Boolean {
        var state by remember { mutableStateOf(true) }

        val clientes = remember { mutableStateListOf<Cliente>() }

        var expandedCliente by remember { mutableStateOf(false) }
        val scrollStateCliente = rememberScrollState()

        var cliente by remember { mutableStateOf<Cliente?>(null) }

        LaunchedEffect(scope) {
            clientes.clear()
            clientes.addAll(Carrinho.mostrarClientes())
        }

        AlertDialog(onDismissRequest = {  state = false},
            title = { Text("Criar Pedido") },
            text = {
                Column {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedCliente = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Cliente: ${cliente?.nome?.value ?: ""}")
                        }
                        DropdownMenu(expanded = expandedCliente,
                            onDismissRequest = { expandedCliente = false },
                            scrollState = scrollStateCliente,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                clientes.forEach {
                                    DropdownMenuItem(text = { Text(it.nome.value) },
                                        onClick = {
                                            cliente = it
                                            expandedCliente = false
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        if (cliente == null) {
                            snackbar.showSnackbar(
                                message = "Erro: cliente não encontrado!",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        } else if (!Carrinho.temItems(cliente!!)) {
                            snackbar.showSnackbar(
                                message = "Erro: não tem itens no carrinho!",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            val idPedido = UUID.randomUUID().toString()

                            val pedido = Pedido(
                                idPedido,
                                cliente!!,
                                Carrinho.mostrarItems(cliente!!),
                                LocalDate.now()
                            )

                            if (controller.cadastrar(pedido)) {
                                snackbar.showSnackbar(
                                    message = "Pedido cadastrado!",
                                    actionLabel = "X",
                                    duration = SnackbarDuration.Short
                                )

                                Carrinho.limparCarrinho(cliente!!)
                            } else {
                                snackbar.showSnackbar(
                                    message = "Erro ao cadastrar pedido",
                                    actionLabel = "X",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = { Button(onClick = { state = false }) { Text("Cancelar") } }
        )

        return state
    }
}