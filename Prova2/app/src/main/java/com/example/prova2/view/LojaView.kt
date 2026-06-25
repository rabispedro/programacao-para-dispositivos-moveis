package com.example.prova2.view

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.prova2.controller.LojaController
import com.example.prova2.controller.QueijoController
import com.example.prova2.model.Loja
import com.example.prova2.model.Queijo
import com.example.prova2.ui.theme.icon.DeleteIcon
import com.example.prova2.ui.theme.icon.EditIcon
import com.example.test.ChevronLeftIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

class LojaView(val lojaController: LojaController, val queijoController: QueijoController) : View {
    override val displayName = "Loja"

    @Composable
    override fun ActionButton(scope: CoroutineScope, snackbar: SnackbarHostState) {
        var estadoEditarLoja by remember { mutableStateOf(false) }
        var estadoRemoverLoja by remember { mutableStateOf(false) }

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { estadoRemoverLoja = true }) {
                Icon(DeleteIcon, "Remover")
            }
            OutlinedButton(onClick = { estadoEditarLoja = true }) {
                Icon(EditIcon, "Editar")
            }
        }


        when {
            estadoEditarLoja -> {
//                estadoEditarLoja = onEditarLoja(scope, snackbar, loja!!)
                LaunchedEffect(scope) {
//                    val lojaEncontrada = lojas.find { it.id == loja!!.id }
//                    lojas.remove(lojaEncontrada)
//                    lojas.add(loja!!)
                }
            }
            estadoRemoverLoja -> {
//                estadoRemoverLoja = onRemoverLoja(scope, snackbar, loja!!)
                LaunchedEffect(scope) {
//                    val lojaEncontrada = lojas.find { it.id == loja!!.id }
//                    lojas.remove(lojaEncontrada)
                }
            }
        }

        val loja = Loja("", "teste", "", "")

        when {
            estadoEditarLoja -> {
                estadoEditarLoja = onEditarLoja(scope, snackbar, loja)
                LaunchedEffect(scope) {

                }
            }
        }
    }

    @Composable
    override fun View(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState) {

        val estadoQueijos = rememberLazyListState()

        val queijos = remember { mutableStateSetOf<Queijo>() }

        var queijo by remember { mutableStateOf<Queijo?>(null) }

        var estadoEditarLoja by remember { mutableStateOf(false) }
        var estadoRemoverLoja by remember { mutableStateOf(false) }

        Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(bottom = 38.dp))

            Row {
//                OutlinedButton(onClick = {
//                    Log.d("debug", "Voltando à pagina principal")
//                    navigator.navigateUp()
//                }) {
//                    Icon(ChevronLeftIcon, "Voltar")
//                }

                Text(text = "Lojinha da Esquina",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(state = estadoQueijos,
                modifier = Modifier.fillMaxWidth().heightIn(480.dp, 500.dp).background(Color.LightGray),
                content = {
                    items(queijos.toList()) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp).heightIn(40.dp)) {

                            Log.d("debug", "Item: $it")

                            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                Text(it.nome.value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text("Tipo: ${it.tipo.capitalize()}")
                                Text("Aroma: ${it.aroma.capitalize()}")
                                Text("Formato Regular: ${it.formatoRegular.capitalize()}")
                                Text("Sabor: ${it.sabor.capitalize()}")
                                Text("Preço: R$${it.preco.value}")
                            }

                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Button(modifier = Modifier.padding(horizontal = 4.dp),
                                    onClick = {
                                        estadoEditarLoja = true
                                        queijo = it
                                    }) {

                                    Icon(EditIcon, "Editar")
                                }

                                Button(modifier = Modifier.padding(horizontal = 4.dp),
                                    onClick = {
                                        estadoRemoverLoja = true
                                        queijo = it
                                    }) {

                                    Icon(DeleteIcon, "Remover")
                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }
    }

    @Composable
    fun onCriarLoja(scope: CoroutineScope, snackbar: SnackbarHostState): Boolean {
        var state by remember { mutableStateOf(true) }

        var nomeLoja by remember { mutableStateOf("") }
        var quantidadeFuncionarioLoja by remember { mutableStateOf("0") }
        var producaoDiariaLoja by remember { mutableStateOf("0") }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Nova Loja") },
            text = {
                Column {
                    OutlinedTextField(value = nomeLoja,
                        onValueChange = {  nomeLoja = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = quantidadeFuncionarioLoja,
                        onValueChange = {  quantidadeFuncionarioLoja = it },
                        label = { Text("Quantidade de Funcionários") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = producaoDiariaLoja,
                        onValueChange = {  producaoDiariaLoja = it },
                        label = { Text("Produção Diária") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        val idLoja = UUID.randomUUID().toString()

                        Log.d("debug", "Id: $idLoja")
                        Log.d("debug", "Nome: $nomeLoja")
                        Log.d("debug", "QuantidadeFuncionario: $quantidadeFuncionarioLoja")
                        Log.d("debug", "ProcucaoDiaria: $producaoDiariaLoja")

                        val loja = Loja(idLoja, nomeLoja, quantidadeFuncionarioLoja, producaoDiariaLoja)

                        if (lojaController.cadastrar(loja)) {
                            snackbar.showSnackbar(
                                message = "Loja criada",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbar.showSnackbar(
                                message = "Erro ao criar loja",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        }
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
    fun onEditarLoja(scope: CoroutineScope, snackbar: SnackbarHostState, loja: Loja): Boolean {
        var state by remember { mutableStateOf(true) }

        var nomeLoja by remember { mutableStateOf(loja.nome.value) }
        var quantidadeFuncionarioLoja by remember { mutableStateOf(loja.quantidadeFuncionario.value.toString()) }
        var producaoDiariaLoja by remember { mutableStateOf(loja.producaoDiaria.value.toString()) }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Editar Loja") },
            text = {
                Column {
                    OutlinedTextField(value = nomeLoja,
                        onValueChange = {  nomeLoja = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = quantidadeFuncionarioLoja,
                        onValueChange = {  quantidadeFuncionarioLoja = it },
                        label = { Text("Quantidade de Funcionários") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = producaoDiariaLoja,
                        onValueChange = {  producaoDiariaLoja = it },
                        label = { Text("Produção Diária") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        val lojaEditada = Loja(loja.id, nomeLoja, quantidadeFuncionarioLoja, producaoDiariaLoja)

                        if (lojaController.alterar(lojaEditada)) {
                            snackbar.showSnackbar(
                                message = "Loja editada",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbar.showSnackbar(
                                message = "Erro ao editar loja",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        }
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
    fun onRemoverLoja(scope: CoroutineScope, snackbar: SnackbarHostState, loja: Loja): Boolean {
        var state by remember { mutableStateOf(true) }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Remover Loja") },
            text = { Text("Deseja remover a loja?") },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        if (lojaController.deletar(loja.id)) {
                            snackbar.showSnackbar(
                                message = "Produto removida",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbar.showSnackbar(
                                message = "Erro ao remover loja",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
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