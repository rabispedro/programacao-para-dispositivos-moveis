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
import com.example.a0xc0ffee.controller.RelatorioController
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.ItemPedido
import com.example.a0xc0ffee.ui.theme.icon.ReportIcon
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate

class RelatorioView(val controller: RelatorioController): View {
    override val displayName = "Relatório"
    override val displayIcon = ReportIcon
    override val tintColor = Color(0xFF8B4513)

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun View(scope: CoroutineScope, snackbar: SnackbarHostState) {
        var estadoClientesPorTipoDoGrao = rememberLazyListState()

        val clientesPorTipoDoGrao = remember { mutableStateListOf<Map<String, List<Cliente>>>() }

        var clienteMaiorValor by remember { mutableStateOf<Cliente?>(null) }
        var clienteMaiorQuantidade by remember { mutableStateOf<Cliente?>(null) }

        val estadoVendasEntreDatas = rememberLazyListState()

        val vendasEntreDatas = remember { mutableStateListOf<ItemPedido>() }

        var dataInicio = remember { mutableStateOf("") }
        var dataFim = remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            clienteMaiorValor = controller.buscarClienteMaiorValor()
            clienteMaiorQuantidade = controller.buscarClienteMaiorQuantidade()

            vendasEntreDatas.clear()
            val vendas = controller.buscarVendasEntreDatas(LocalDate.now(), LocalDate.now())
            vendasEntreDatas.addAll(vendas)

            Log.d("debug", "Vendas: ${vendas.size}")

            clientesPorTipoDoGrao.clear()
            val clientes = controller.listarClientesPorTipoDoGrao()

            clientes.forEach { key, cliente ->
//                clientesPorTipoDoGrao.get(key).addAll(cliente)
            }

            Log.d("debug", "Clientes por tipo de Grão: ${vendas.size}")
        }

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.padding(bottom = 30.dp))

            Text(text = "Relatório",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth().padding(8.dp))

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

//            Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 4.dp) {
            LazyColumn(
                state = estadoClientesPorTipoDoGrao,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(480.dp, 500.dp)
                    .background(Color.LightGray),
                content = {
                    items(clientesPorTipoDoGrao) {
//                        produtos.forEach {
                        Row (horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray)
                                .padding(8.dp)
                                .heightIn(40.dp)) {

                            Log.d("debug", "Item: $it")

                            Column(verticalArrangement = Arrangement.SpaceEvenly) {
//                                Text(text = "${it.nome.value}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//                                Text(text = "${it.cpf.safeShow()}", fontWeight = FontWeight.Bold)
//                                Text("${it.instagram.value}", fontWeight = FontWeight.Light)
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 16.dp))

//            Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 4.dp) {
            LazyColumn(
                state = estadoVendasEntreDatas,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(480.dp, 500.dp)
                    .background(Color.LightGray),
                content = {
                    items(vendasEntreDatas) {
//                        produtos.forEach {
                        Row (horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray)
                                .padding(8.dp)
                                .heightIn(40.dp)) {

                            Log.d("debug", "Item: $it")

                            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                                Text(text = "${it.id}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(text = "${it.idProduto}", fontWeight = FontWeight.Bold)
                                Text(text = "${it.quantidade}", fontWeight = FontWeight.Light)
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )
        }
    }
}