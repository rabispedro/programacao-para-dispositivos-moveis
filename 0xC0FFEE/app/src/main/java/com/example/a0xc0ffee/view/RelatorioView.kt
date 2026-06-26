package com.example.a0xc0ffee.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a0xc0ffee.controller.RelatorioController
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.Pedido
import com.example.a0xc0ffee.ui.theme.icon.ReportIcon
import kotlinx.coroutines.CoroutineScope
import kotlin.collections.forEach

class RelatorioView(val controller: RelatorioController): View {
    override val displayName = "Relatório"
    override val displayIcon = ReportIcon
    override val tintColor = Color(0xFF8B4513)

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun View(scope: CoroutineScope, snackbar: SnackbarHostState) {
        val estadoClientesPorTipoDoGrao = rememberLazyListState()
        val clientesPorTipoDoGrao = remember { mutableStateMapOf<String, MutableList<Cliente>>() }

        val estadoClienteMaiorValor = rememberLazyListState()
        val clienteMaiorValor = remember { mutableStateListOf<Cliente>() }

        val estadoClienteMaiorQuantidade = rememberLazyListState()
        val clienteMaiorQuantidade = remember { mutableStateListOf<Cliente>() }

        val estadoVendasEntreDatas = rememberLazyListState()

        val vendasEntreDatas = remember { mutableStateListOf<Pedido>() }

        var dataInicio = remember { mutableStateOf("") }
        var dataFim = remember { mutableStateOf("") }

        LaunchedEffect(scope) {
            controller.buscarClienteMaiorValor {
                clienteMaiorValor.clear()
                clienteMaiorValor.addAll(it)
            }

            controller.buscarClienteMaiorQuantidade {
                clienteMaiorQuantidade.clear()
                clienteMaiorQuantidade.addAll(it)
            }

            vendasEntreDatas.clear()
//            val vendas = controller.buscarVendasEntreDatas(dataInicio, dataFim)
//            vendasEntreDatas.addAll(vendas)

            Log.d("debug", "Vendas: ${vendasEntreDatas.size}")

            controller.listarClientesPorTipoDoGrao() {
                clientesPorTipoDoGrao.clear()
                it.keys.forEach { entity ->
                    clientesPorTipoDoGrao[entity] = it[entity]!!.toMutableList()
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(bottom = 30.dp))

            Text(text = "Relatório",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth().padding(8.dp))

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            LazyColumn(state = estadoClienteMaiorValor,
                modifier = Modifier.fillMaxWidth().heightIn(100.dp, 140.dp).background(Color.LightGray),
                content = {
                    Log.d("debug", "Maior valor: ${clienteMaiorValor.size}")
                    item {
                        Text("Maior valor", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                    items(clienteMaiorValor) {
                        Row (horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp).heightIn(40.dp)) {

                            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                Text(it.nome.value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(it.cpf.safeShow(), fontWeight = FontWeight.Bold)
                                Text(it.instagram.value, fontWeight = FontWeight.Light)
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(state = estadoClienteMaiorQuantidade,
                modifier = Modifier.fillMaxWidth().heightIn(100.dp, 140.dp).background(Color.LightGray),
                content = {
                    Log.d("debug", "Maior quantidade: ${clienteMaiorQuantidade.size}")
                    item {
                        Text("Maior quantidade", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                    items(clienteMaiorQuantidade) {
                        Row (horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp).heightIn(40.dp)) {

                            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                Text(it.nome.value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(it.cpf.safeShow(), fontWeight = FontWeight.Bold)
                                Text(it.instagram.value, fontWeight = FontWeight.Light)
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(state = estadoClientesPorTipoDoGrao,
                modifier = Modifier.fillMaxWidth().heightIn(160.dp, 180.dp).background(Color.LightGray),
                content = {
                    clientesPorTipoDoGrao.forEach { (key, value) ->
                        Log.d("debug", "GRÃO $key")
                        item {
                            Text("Grão $key", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.padding(bottom = 4.dp))
                        }
                        items(value) {
                            Row (horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp).heightIn(40.dp)) {

                                Log.d("debug", "Item: $it")

                                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                    Text(text = it.nome.value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    Text(text = it.cpf.safeShow(), fontWeight = FontWeight.Bold)
                                    Text(it.instagram.value, fontWeight = FontWeight.Light)
                                }
                            }

                            Spacer(modifier = Modifier.padding(bottom = 4.dp))
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(state = estadoVendasEntreDatas,
                modifier = Modifier.fillMaxWidth().heightIn(160.dp, 180.dp).background(Color.LightGray),
                content = {
                    items(vendasEntreDatas) {
                        Row (horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp).heightIn(40.dp)) {

                            Log.d("debug", "Item: $it")
                            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                Text(text = it.id, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(text = "${it.data}", fontWeight = FontWeight.Bold)
                                Text(text = "${it.data}", fontWeight = FontWeight.Light)
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )
        }
    }
}