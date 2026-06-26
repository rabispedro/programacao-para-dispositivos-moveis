package com.example.a0xc0ffee.view

import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a0xc0ffee.controller.ProdutoController
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.Produto
import com.example.a0xc0ffee.model.aggregate.Carrinho
import com.example.a0xc0ffee.model.type.PontoDaTorra
import com.example.a0xc0ffee.model.type.TipoDoGrao
import com.example.a0xc0ffee.ui.theme.icon.AddShoppingCartIcon
import com.example.a0xc0ffee.ui.theme.icon.DeleteIcon
import com.example.a0xc0ffee.ui.theme.icon.EditIcon
import com.example.a0xc0ffee.ui.theme.icon.LocalCoffeeIcon
import com.example.a0xc0ffee.ui.theme.icon.PlusIcon
import com.example.a0xc0ffee.ui.theme.icon.SearchIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

class ProdutoView(val controller: ProdutoController): View {
    override val displayName = "Produto"
    override val displayIcon = LocalCoffeeIcon
    override val tintColor = Color(0xFF8B4513)

    private val TAMANHO_TEXTO_PARA_BUSCAR = 7

    @Composable
    override fun View(scope: CoroutineScope, snackbar: SnackbarHostState) {
        val estadoProdutos = rememberLazyListState()
        val produtos = remember { mutableStateListOf<Produto>() }

        var estadoCriarProduto by remember { mutableStateOf(false) }
        var estadoEditarProduto by remember { mutableStateOf(false) }
        var estadoRemoverProduto by remember { mutableStateOf(false) }

        var estadoAdicionarProduto by remember { mutableStateOf(false) }
        var produto by remember { mutableStateOf<Produto?>(null) }

        var estadoBuscarProduto by remember { mutableStateOf(false) }
        var textoBuscaProduto by remember { mutableStateOf("") }

        LaunchedEffect(scope) {
            controller.listar {
                produtos.clear()
                produtos.addAll(it)
            }
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(bottom = 30.dp))

            Text(text = "Produtos",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            TextField(value = textoBuscaProduto,
                onValueChange = {
                    textoBuscaProduto = it.trim()
                    estadoBuscarProduto = (textoBuscaProduto.length >= TAMANHO_TEXTO_PARA_BUSCAR)
                },
                leadingIcon = { Icon(SearchIcon, "Lupa") },
                placeholder = { Text("Um café gostoso é o...") },
                modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp)
            )

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            LazyColumn(state = estadoProdutos,
                modifier = Modifier.fillMaxWidth().heightIn(480.dp, 500.dp).background(Color.LightGray),
                content = {
                    items(produtos) {
                        Row (horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp).heightIn(40.dp)) {

                                Log.d("debug", "Item: $it")

                                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                    Text(text = it.tipoDoGrao.localize(), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    Text(text = it.pontoDaTorra.localize(), fontWeight = FontWeight.Bold)
                                    Text("R$: ${it.valor}")
                                    Text(text = if (it.blend) "Blendelicioso" else "Desblendado", fontWeight = FontWeight.Light)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    IconButton(modifier = Modifier.padding(horizontal = 4.dp),
                                        onClick = {
                                            estadoAdicionarProduto = true
                                            produto = it
                                        }) {

                                        Icon(AddShoppingCartIcon, "Adicionar ao carrinho", tint = Color.White)
                                    }

                                    IconButton(modifier = Modifier.padding(horizontal = 4.dp),
                                        onClick = {
                                            estadoEditarProduto = true
                                            produto = it
                                        }) {

                                        Icon(EditIcon, "Editar", tint = Color.White)
                                    }

                                    IconButton(modifier = Modifier.padding(horizontal = 4.dp),
                                        onClick = {
                                            estadoRemoverProduto = true
                                            produto = it
                                        }) {

                                        Icon(DeleteIcon, "Remover", tint = Color.White)
                                    }
                                }
                            }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 10.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp), horizontalArrangement = Arrangement.End) {
                Button(onClick = { estadoCriarProduto = true }, modifier = Modifier.padding(vertical = 4.dp)) {
                    Icon(PlusIcon, "Adicionar")
                }
            }

            when {
                estadoCriarProduto -> {
                    estadoCriarProduto = onCriarProduto(scope, snackbar)
                }
                estadoBuscarProduto -> {
                    LaunchedEffect(scope) {
                        controller.listar(textoBuscaProduto) {
                            produtos.clear()
                            produtos.addAll(it)
                        }
                        Log.d("debug", "Produtos: ${produtos.size}")
                        estadoBuscarProduto = false
                        textoBuscaProduto = ""
                    }
                }
                estadoEditarProduto -> {
                    estadoEditarProduto = onEditarProduto(scope, snackbar, produto!!)
                }
                estadoRemoverProduto -> {
                    estadoRemoverProduto = onRemoverProduto(scope, snackbar, produto!!)
                }
                estadoAdicionarProduto -> {
                    estadoAdicionarProduto = onAdicionarProdutoAoCarrinho(scope, snackbar, produto!!)
                }
            }
        }
    }

    @Composable
    fun onCriarProduto(scope: CoroutineScope, snackbar: SnackbarHostState): Boolean {
        var state by remember { mutableStateOf(true) }

        var expandedTipoDoGrao by remember { mutableStateOf(false) }
        val scrollStateTipoDoGrao = rememberScrollState()

        var expandedPontoDaTorra by remember { mutableStateOf(false) }
        val scrollStatePontoDaTorra = rememberScrollState()

        var tipoDoGraoProduto by remember { mutableStateOf("ARABICA") }
        var pontoDaTorraProduto by remember { mutableStateOf("MEDIA") }
        var valorProduto by remember { mutableStateOf("0.0") }
        var blendProduto by remember { mutableStateOf(false) }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Novo Produto") },
            text = {
                Column {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedTipoDoGrao = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Tipo do Grão: $tipoDoGraoProduto")
                        }
                        DropdownMenu(expanded = expandedTipoDoGrao,
                            onDismissRequest = { expandedTipoDoGrao = false },
                            scrollState = scrollStateTipoDoGrao,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                TipoDoGrao.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.localize()) },
                                        onClick = {
                                            tipoDoGraoProduto = it.name
                                            expandedTipoDoGrao = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedPontoDaTorra = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Ponto da Torra: $pontoDaTorraProduto")
                        }
                        DropdownMenu(expanded = expandedPontoDaTorra,
                            onDismissRequest = { expandedPontoDaTorra = false },
                            scrollState = scrollStatePontoDaTorra,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                PontoDaTorra.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.localize()) },
                                        onClick = {
                                            pontoDaTorraProduto = it.name
                                            expandedPontoDaTorra = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = valorProduto,
                        onValueChange = {  valorProduto = it.trim() },
                        label = { Text("Valor") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {

                        Text("É um blend?")
                        Checkbox(checked = blendProduto, onCheckedChange = { blendProduto = !blendProduto })
                        Text(if (blendProduto) "Com toda certeza!" else "Não sei ao certo.")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        val idProduto = UUID.randomUUID().toString()
                        val produto = Produto(
                            idProduto,
                            TipoDoGrao.valueOf(tipoDoGraoProduto),
                            PontoDaTorra.valueOf(pontoDaTorraProduto),
                            valorProduto.toDouble(),
                            blendProduto
                        )

                        var result = Pair(false, "")

                        controller.cadastrar(produto) {
                            result = it
                        }

                        snackbar.showSnackbar(
                            message = result.second,
                            actionLabel = "X",
                            duration = SnackbarDuration.Short
                        )
                    }
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = { Button(onClick = { state = false } ) { Text("Cancelar") } }
        )

        return state
    }

    @Composable
    fun onEditarProduto(scope: CoroutineScope, snackbar: SnackbarHostState, produto: Produto): Boolean {
        var state by remember { mutableStateOf(true) }

        var expandedTipoDoGrao by remember { mutableStateOf(false) }
        val scrollStateTipoDoGrao = rememberScrollState()

        var expandedPontoDaTorra by remember { mutableStateOf(false) }
        val scrollStatePontoDaTorra = rememberScrollState()

        var tipoDoGraoProduto by remember { mutableStateOf(produto.tipoDoGrao.name) }
        var pontoDaTorraProduto by remember { mutableStateOf(produto.pontoDaTorra.name) }
        var valorProduto by remember { mutableStateOf(produto.valor.toString()) }
        var blendProduto by remember { mutableStateOf(produto.blend) }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Editar Produto") },
            text = {
                Column {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedTipoDoGrao = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Tipo do Grão: $tipoDoGraoProduto")
                        }
                        DropdownMenu(expanded = expandedTipoDoGrao,
                            onDismissRequest = { expandedTipoDoGrao = false },
                            scrollState = scrollStateTipoDoGrao,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                TipoDoGrao.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.localize()) },
                                        onClick = {
                                            tipoDoGraoProduto = it.name
                                            expandedTipoDoGrao = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedPontoDaTorra = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Ponto da Torra: $pontoDaTorraProduto")
                        }
                        DropdownMenu(expanded = expandedPontoDaTorra,
                            onDismissRequest = { expandedPontoDaTorra = false },
                            scrollState = scrollStatePontoDaTorra,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                PontoDaTorra.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.localize()) },
                                        onClick = {
                                            pontoDaTorraProduto = it.name
                                            expandedPontoDaTorra = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = valorProduto,
                        onValueChange = {  valorProduto = it.trim() },
                        label = { Text("Valor") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {

                        Text("É um blend?")
                        Checkbox(checked = blendProduto, onCheckedChange = { blendProduto = !blendProduto })
                        Text(if (blendProduto) "Com toda certeza!" else "Não sei ao certo.")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        val produtoEditado = Produto(
                            produto.id,
                            TipoDoGrao.valueOf(tipoDoGraoProduto),
                            PontoDaTorra.valueOf(pontoDaTorraProduto),
                            valorProduto.toDouble(),
                            blendProduto
                        )

                        var result = Pair(false, "")
                        controller.alterar(produtoEditado) {
                            result = it
                        }

                        snackbar.showSnackbar(
                            message = result.second,
                            actionLabel = "X",
                            duration = SnackbarDuration.Short
                        )
                    }
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = { Button(onClick = { state = false } ) { Text("Cancelar") } }
        )

        return state
    }

    @Composable
    fun onRemoverProduto(scope: CoroutineScope, snackbar: SnackbarHostState, produto: Produto): Boolean {
        var state by remember { mutableStateOf(true) }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Remover Produto") },
            text = { Text("Deseja remover o produto?") },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        var result = Pair(false, "")
                        controller.deletar(produto.id) {
                            result = it
                        }
                        snackbar.showSnackbar(
                            message = result.second,
                            actionLabel = "X",
                            duration = SnackbarDuration.Short
                        )
                    }
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = { Button(onClick = { state = false }) { Text("Cancelar") } }
        )

        return state
    }

    @Composable
    fun onAdicionarProdutoAoCarrinho(scope: CoroutineScope, snackbar: SnackbarHostState, produto: Produto): Boolean {
        var state by remember { mutableStateOf(true) }

        val clientes = remember { SnapshotStateList<Cliente>() }

        var expandedCliente by remember { mutableStateOf(false) }
        val scrollStateCliente = rememberScrollState()

        var cliente by remember { mutableStateOf<Cliente?>(null) }

        var quantidadeProduto by remember { mutableStateOf("1") }

        LaunchedEffect(scope) {
            controller.listarClientes() {
                clientes.clear()
                clientes.addAll(it)
            }
            Log.d("debug", "Clientes encontrados: ${clientes.size}")
        }

        AlertDialog(onDismissRequest = {  state = false},
            title = { Text("Adicionar Produto ao Carrinho") },
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

                    OutlinedTextField(value = quantidadeProduto,
                        onValueChange = {  quantidadeProduto = it.trim() },
                        label = { Text("Quantidade") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
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
                        } else if (quantidadeProduto.toInt() <= 0) {
                            snackbar.showSnackbar(
                                message = "Erro: quantidade de produto inválida!",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            Carrinho.adicionarItem(cliente!!, produto, quantidadeProduto.toInt())
                            snackbar.showSnackbar(
                                message = "Produto adicionado ao carrinho!",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }) {
                    Text("Adicionar item")
                }
            },
            dismissButton = { Button(onClick = { state = false }) { Text("Cancelar") } }
        )

        return state
    }
}