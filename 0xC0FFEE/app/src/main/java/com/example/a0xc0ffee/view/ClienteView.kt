package com.example.a0xc0ffee.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a0xc0ffee.controller.ClienteController
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.ui.theme.icon.DeleteIcon
import com.example.a0xc0ffee.ui.theme.icon.EditIcon
import com.example.a0xc0ffee.ui.theme.icon.PlusIcon
import com.example.a0xc0ffee.ui.theme.icon.SearchIcon
import com.example.a0xc0ffee.ui.theme.icon.StoreFrontIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ClienteView(val controller: ClienteController): View {
    override val displayName = "Cliente"
    override val displayIcon = StoreFrontIcon
    override val tintColor = Color(0xFF8B4513)

    private val TAMANHO_TEXTO_PARA_BUSCAR = 11

    @Composable
    override fun View(scope: CoroutineScope, snackbar: SnackbarHostState) {
        val estadoClientes = rememberLazyListState()
        val clientes = remember { mutableStateListOf<Cliente>() }

        var estadoCriarCliente by remember { mutableStateOf(false) }
        var estadoEditarCliente by remember { mutableStateOf(false) }
        var estadoRemoverCliente by remember { mutableStateOf(false) }

        var cliente by remember { mutableStateOf<Cliente?>(null) }

        var estadoBuscarCliente by remember { mutableStateOf(false) }
        var textoBuscaCliente by remember { mutableStateOf("") }

        LaunchedEffect(scope) {
            controller.listar {
                clientes.clear()
                clientes.addAll(it)
            }
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(bottom = 30.dp))

            Text(text = "Clientes",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth().padding(8.dp))

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            TextField(value = textoBuscaCliente,
                onValueChange = {
                    textoBuscaCliente = it.trim()
                    estadoBuscarCliente = (textoBuscaCliente.length >= TAMANHO_TEXTO_PARA_BUSCAR)
                },
                leadingIcon = { Icon(SearchIcon, "Lupa") },
                placeholder = { Text("O CPF que eu quero é...") },
                modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp)
            )

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            LazyColumn(state = estadoClientes,
                modifier = Modifier.fillMaxWidth().heightIn(480.dp, 500.dp).background(Color.LightGray),
                content = {
                    items(clientes) {
                        Row (horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp).heightIn(40.dp)) {

                            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                Text(it.nome.value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(it.cpf.safeShow(), fontWeight = FontWeight.Bold)
                                Text(it.instagram.value, fontWeight = FontWeight.Light)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                IconButton(modifier = Modifier.padding(horizontal = 4.dp),
                                    onClick = {
                                        estadoEditarCliente = true
                                        cliente = it
                                    }) {

                                    Icon(EditIcon, "Editar", tint = Color.White)
                                }

                                IconButton(modifier = Modifier.padding(horizontal = 4.dp),
                                    onClick = {
                                        estadoRemoverCliente = true
                                        cliente = it
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
                Button(onClick = { estadoCriarCliente = true }, modifier = Modifier.padding(vertical = 4.dp)) {
                    Icon(PlusIcon, "Adicionar")
                }
            }

            when {
                estadoCriarCliente -> {
                    estadoCriarCliente = onCriarCliente(scope, snackbar)
                }
                estadoBuscarCliente -> {
                    LaunchedEffect(scope) {
                        controller.listar(textoBuscaCliente) {
                            clientes.clear()
                            clientes.addAll(it)
                        }

                        Log.d("debug", "Clientes: ${clientes.size}")
                        textoBuscaCliente = ""
                        estadoBuscarCliente = false
                    }
                }
                estadoEditarCliente -> {
                    estadoEditarCliente = onEditarCliente(scope, snackbar, cliente!!)
                }
                estadoRemoverCliente -> {
                    estadoRemoverCliente = onRemoverCliente(scope, snackbar, cliente!!)
                }
            }
        }
    }

    @Composable
    fun onCriarCliente(scope: CoroutineScope, snackbar: SnackbarHostState): Boolean {
        var state by remember { mutableStateOf(true) }

        var cpfCliente by remember { mutableStateOf("") }
        var nomeCliente by remember { mutableStateOf("") }
        var telefoneCliente by remember { mutableStateOf("") }
        var enderecoCliente by remember { mutableStateOf("") }
        var instagramCliente by remember { mutableStateOf("") }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Novo Produto") },
            text = {
                Column {
                    OutlinedTextField(value = cpfCliente,
                        onValueChange = {  cpfCliente = it.trim() },
                        label = { Text("CPF") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = nomeCliente,
                        onValueChange = {  nomeCliente = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = telefoneCliente,
                        onValueChange = {  telefoneCliente = it.trim() },
                        label = { Text("Telefone") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = enderecoCliente,
                        onValueChange = {  enderecoCliente = it },
                        label = { Text("Endereço") },
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = instagramCliente,
                        onValueChange = {  instagramCliente = it.trim() },
                        label = { Text("Instagram") },
                        modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        val cliente = Cliente(
                            cpfCliente,
                            nomeCliente,
                            telefoneCliente,
                            enderecoCliente,
                            instagramCliente
                        )

                        var result = Pair(false, "")

                        controller.cadastrar(cliente) {
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
    fun onEditarCliente(scope: CoroutineScope, snackbar: SnackbarHostState, cliente: Cliente): Boolean {
        var state by remember { mutableStateOf(true) }

        var nomeCliente by remember { mutableStateOf(cliente.nome.value) }
        var telefoneCliente by remember { mutableStateOf(cliente.telefone.value) }
        var enderecoCliente by remember { mutableStateOf(cliente.endereco.value) }
        var instagramCliente by remember { mutableStateOf(cliente.instagram.value) }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Editar Cliente") },
            text = {
                Column {
                    OutlinedTextField(value = nomeCliente,
                        onValueChange = {  nomeCliente = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = telefoneCliente,
                        onValueChange = {  telefoneCliente = it.trim() },
                        label = { Text("Telefone") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = enderecoCliente,
                        onValueChange = {  enderecoCliente = it },
                        label = { Text("Endereço") },
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = instagramCliente,
                        onValueChange = { instagramCliente = it.trim() },
                        label = { Text("Instagram") },
                        modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        val clienteEditado = Cliente(
                            cliente.cpf.value,
                            nomeCliente,
                            telefoneCliente,
                            enderecoCliente,
                            instagramCliente
                        )

                        var result = Pair(false, "")

                        controller.alterar(clienteEditado) {
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
    fun onRemoverCliente(scope: CoroutineScope, snackbar: SnackbarHostState, cliente: Cliente): Boolean {
        var state by remember { mutableStateOf(true) }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Remover Cliente") },
            text = { Text("Deseja remover o cliente?") },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        var result = Pair(false, "")

                        controller.deletar(cliente.cpf.value) {
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
}
