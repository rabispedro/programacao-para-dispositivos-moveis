package com.example.prova2.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.navigation.NavHostController
import com.example.prova2.controller.LojaController
import com.example.prova2.controller.QueijoController
import com.example.prova2.model.Loja
import com.example.prova2.model.Queijo
import com.example.prova2.ui.theme.icon.PlusIcon
import com.example.prova2.ui.theme.icon.StoreFrontIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

class MainView(val lojaController: LojaController, val queijoController: QueijoController) : View {
    @Composable
    override fun TopBar(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState) {
        Text(text = "Minas Gerais Queijos e Cia",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
    }

    @Composable
    override fun ActionButton(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState) {
        var estadoCriarLoja by remember { mutableStateOf(false) }

        OutlinedButton(onClick = { estadoCriarLoja = true }) {
            Icon(PlusIcon, "Adicionar")
        }

        when {
            estadoCriarLoja -> {
                estadoCriarLoja = onCriarLoja(scope, snackbar)
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    override fun View(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState) {
        val estadoLojas = rememberLazyListState()

        var estadoEscolheLoja by remember { mutableStateOf(false) }

        val lojas = remember { mutableStateListOf<Loja>() }

        var queijoMaiorAroma by remember { mutableStateOf<Queijo?>(null) }
        var queijoMaisCaro by remember { mutableStateOf<Queijo?>(null) }

        var lojaComQueijosMaisFeios by remember { mutableStateOf<Loja?>(null) }

        var quantidadeQueijos by remember { mutableStateOf(0) }
        var quantidadeLojas by remember { mutableStateOf(0) }

        LaunchedEffect(scope) {
            val entities = lojaController.listar()
            val entityMaiorAroma = queijoController.listarQueijoMaiorAroma()
            val entityMaisCaro = queijoController.listarQueijoMaiosCaro()
            val entityLojaComQueijosMaisFeios = queijoController.listarQueijosAparenciaRuim()
            val entityQuantidadeLojas = lojaController.contar()
            val entityQuantidadeQueijos = queijoController.contar()

            lojas.addAll(entities)
            queijoMaiorAroma = entityMaiorAroma.firstOrNull()
            queijoMaisCaro = entityMaisCaro.firstOrNull()
            lojaComQueijosMaisFeios = entityLojaComQueijosMaisFeios.firstOrNull()
            quantidadeLojas = entityQuantidadeLojas
            quantidadeQueijos = entityQuantidadeQueijos
        }

        Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            Text("Unidades abertas: ${lojas.size}", fontSize = 20.sp)

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(state = estadoLojas,
                modifier = Modifier.fillMaxWidth().heightIn(350.dp, 400.dp).background(Color.LightGray),
                content = {
                    items(lojas.toList()) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().padding(4.dp).heightIn(40.dp)) {

                            Log.d("debug", "Item: $it")

                            Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                                Icon(StoreFrontIcon, "Loja", tint = Color.DarkGray)
                            }

                            Card(modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    estadoEscolheLoja = true
                                    DataSource.setLoja(it)
                                }
                            ) {
                                Text(
                                    it.nome.value,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )

                                Text(
                                    "Funcionários: ${it.quantidadeFuncionario.value}",
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )

                                Text(
                                    "Produção Diária: ${it.producaoDiaria.value}",
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 12.dp))

            Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                Text("Quantidade de Lojas: $quantidadeLojas", fontSize = 20.sp)

                Spacer(modifier = Modifier.padding(bottom = 12.dp))

                Text("Quantidade de Queijos: $quantidadeQueijos", fontSize = 20.sp)

                Spacer(modifier = Modifier.padding(bottom = 12.dp))

                Text("Queijo com maior aroma: ${queijoMaiorAroma?.nome?.value} (${queijoMaiorAroma?.aroma?.capitalize()})", fontSize = 20.sp)

                Spacer(modifier = Modifier.padding(bottom = 12.dp))

                Text("Queijo mais caro: ${queijoMaisCaro?.nome?.value} (R$${queijoMaisCaro?.preco?.value})", fontSize = 20.sp)

                Spacer(modifier = Modifier.padding(bottom = 12.dp))

                Text("Loja com queijos mais feios: ${lojaComQueijosMaisFeios?.nome?.value}", fontSize = 20.sp)
            }
        }

        when {
            estadoEscolheLoja -> {
                estadoEscolheLoja = false
                navigator.navigate("LojaView")
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
}