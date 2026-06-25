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
import androidx.compose.material3.Card
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
import androidx.compose.runtime.snapshots.SnapshotStateSet
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
import com.example.prova2.model.Loja
import com.example.prova2.ui.theme.icon.PlusIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

class MainView(val controller: LojaController) : View {
    override val displayName = "Main"
    lateinit var lojas: SnapshotStateSet<Loja>

    @Composable
    override fun ActionButton(scope: CoroutineScope, snackbar: SnackbarHostState) {
        var estadoCriarLoja by remember { mutableStateOf(false) }

        OutlinedButton(onClick = { estadoCriarLoja = true }) {
            Icon(PlusIcon, "Adicionar")
        }

        when {
            estadoCriarLoja -> {
                estadoCriarLoja = onCriarLoja(scope, snackbar)
                LaunchedEffect(scope) {
                    val entities = controller.listar()
                    lojas.addAll(entities)
                    Log.d("debug", "Lojas: ${lojas.size}")
                }
            }
        }
    }

    @Composable
    override fun View(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState) {
        Log.d("debug", "Main Navigator: $navigator")

        val estadoLojas = rememberLazyListState()
        lojas = remember { mutableStateSetOf() }

        var estadoEscolheLoja by remember { mutableStateOf(false) }

        LaunchedEffect(scope) {
            val entities = controller.listar()
            lojas.addAll(entities)
            Log.d("debug", "Lojas: ${lojas.size}")
        }

        Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(bottom = 38.dp))

            Text(text = "Minas Gerais Queijos e Cia",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            Spacer(modifier = Modifier.padding(bottom = 32.dp))

            Text("Unidades abertas", fontSize = 20.sp)

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(state = estadoLojas,
                modifier = Modifier.fillMaxWidth().heightIn(480.dp, 500.dp).background(Color.LightGray),
                content = {
                    items(lojas.toList()) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().padding(4.dp).heightIn(40.dp)) {

                            Log.d("debug", "Item: $it")

                                Card(modifier = Modifier.fillMaxWidth(),
                                    onClick = {
//                                        lojaView.escolheLoja(it)
                                        estadoEscolheLoja = true
                                        Log.d("debug", "Navegando para a loja: ${it.nome.value}")
                                    }
                                ) {
                                    Text(it.nome.value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    Text("Funcionários: ${it.quantidadeFuncionario.value}")
                                    Text("Produção Diária: ${it.producaoDiaria.value}")
                                }

                        }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }

        when {
            estadoEscolheLoja -> {
                estadoEscolheLoja = false
                navigator.navigate("Loja")
            }
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

                        if (controller.cadastrar(loja)) {
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
}