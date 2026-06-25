package com.example.prova2.view

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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import com.example.prova2.controller.LojaController
import com.example.prova2.controller.QueijoController
import com.example.prova2.model.Loja
import com.example.prova2.model.Queijo
import com.example.prova2.model.type.AparenciaAgradavel
import com.example.prova2.model.type.AromaQueijo
import com.example.prova2.model.type.FormatoRegularQueijo
import com.example.prova2.model.type.SaborQueijo
import com.example.prova2.model.type.TipoQueijo
import com.example.prova2.ui.theme.icon.DeleteIcon
import com.example.prova2.ui.theme.icon.EditIcon
import com.example.prova2.ui.theme.icon.MoreVertIcon
import com.example.prova2.ui.theme.icon.PlusIcon
import com.example.test.ChevronLeftIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

class LojaView(val lojaController: LojaController, val queijoController: QueijoController) : View {
    @Composable
    override fun TopBar(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState) {
        var estadoConfiguracoes by remember { mutableStateOf(false) }
        val scroll = rememberScrollState()

        var estadoEditarLoja by remember { mutableStateOf(false) }
        var estadoRemoverLoja by remember { mutableStateOf(false) }

        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            IconButton(onClick = { navigator.navigate("MainView") }) {
                Icon(ChevronLeftIcon, "Voltar")
            }

            Text(text = DataSource.getLoja()?.nome?.value!!,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(8.dp)
            )

            Box {
                IconButton(onClick = { estadoConfiguracoes = true }) {
                    Icon(MoreVertIcon, "Opções")
                }
                DropdownMenu(expanded = estadoConfiguracoes,
                    onDismissRequest = { estadoConfiguracoes = false },
                    scrollState = scroll,
                    content = {
                        DropdownMenuItem(onClick = { estadoEditarLoja = true },
                            text = { Text("Editar", fontSize = 18.sp) },
                            leadingIcon = { Icon(EditIcon, "Editar") }
                        )

                        HorizontalDivider(thickness = 1.dp)

                        DropdownMenuItem(onClick = { estadoRemoverLoja = true },
                            text = { Text("Remover", fontSize = 18.sp) },
                            leadingIcon = { Icon(DeleteIcon, "Remover") }
                        )

                    }
                )
            }
        }

        when {
            estadoEditarLoja -> {
                estadoConfiguracoes = false
                estadoEditarLoja = onEditarLoja(scope, snackbar, DataSource.getLoja()!!)
            }
            estadoRemoverLoja -> {
                estadoConfiguracoes = false
                estadoRemoverLoja = onRemoverLoja(navigator, scope, snackbar, DataSource.getLoja()!!)
            }
        }
    }

    @Composable
    override fun ActionButton(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState) {
        var estadoCriarQueijo by remember { mutableStateOf(false) }

        OutlinedButton(onClick = { estadoCriarQueijo = true }) {
            Icon(PlusIcon, "Adicionar")
        }

        when {
            estadoCriarQueijo -> {
                estadoCriarQueijo = onCriarQueijo(scope, snackbar)
            }
        }
    }

    @Composable
    override fun View(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState) {
        Log.d("debug", "Loja Navigator: $navigator")

        val estadoQueijos = rememberLazyListState()

        var estadoEditarQueijo by remember { mutableStateOf(false) }
        var estadoRemoverQueijo by remember { mutableStateOf(false) }

        val queijos = remember { mutableStateSetOf<Queijo>() }

        var queijo by remember { mutableStateOf<Queijo?>(null) }

        val valorMedioQueijos = remember { mutableDoubleStateOf(0.0) }

        LaunchedEffect(scope) {
            val entities = queijoController.listar(DataSource.getLoja()!!)
            val entityMediaQueijos = queijoController.listarValorMedioQueijos(DataSource.getLoja()!!)

            Log.d("debug", "Entity Valor Médio dos Queijos: $entityMediaQueijos")

            queijos.addAll(entities)
            valorMedioQueijos.doubleValue = entityMediaQueijos

            Log.d("debug", "State Valor Médio dos Queijos: $valorMedioQueijos")
        }

        Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(state = estadoQueijos,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(480.dp, 500.dp)
                    .background(Color.LightGray),
                content = {
                    items(queijos.toList()) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp).heightIn(40.dp)) {

                            Log.d("debug", "Item: $it")

                            Column {
                                Text(it.nome.value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                Text("Tipo: ${it.tipo.capitalize()}")
                                Text("Aroma: ${it.aroma.capitalize()}")
                                Text("Aparência Agradável: ${it.aparenciaAgradavel.capitalize()}")
                                Text("Formato Regular: ${it.formatoRegular.capitalize()}")
                                Text("Sabor: ${it.sabor.capitalize()}")
                                Text("Preço: R$${it.preco.value}")
                            }
                            Column {
                                IconButton(onClick = {
                                    queijo = it
                                    estadoEditarQueijo = true
                                }) {
                                    Icon(EditIcon, "Alterar")
                                }

                                IconButton(onClick = {
                                    queijo = it
                                    estadoRemoverQueijo = true
                                }) {
                                    Icon(DeleteIcon, "Remover")
                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 12.dp))

            Text("Valor Médio dos Queijos: R$${valorMedioQueijos.doubleValue}", fontSize = 20.sp)
        }

        when {
            estadoEditarQueijo -> {
                estadoEditarQueijo = onEditarQueijo(scope, snackbar, queijo!!)
            }
            estadoRemoverQueijo -> {
                estadoRemoverQueijo = onRemoverQueijo(scope, snackbar, queijo!!)
            }
        }
    }

    @Composable
    fun onCriarQueijo(scope: CoroutineScope, snackbar: SnackbarHostState): Boolean {
        var state by remember { mutableStateOf(true) }

        val scrollTipoQueijo = rememberScrollState()
        val scrollAromaQueijo = rememberScrollState()
        val scrollAparenciaAgradavelQueijo = rememberScrollState()
        val scrollFormatoRegularQueijo = rememberScrollState()
        val scrollSaborQueijo = rememberScrollState()

        var expandedTipoQueijo by remember { mutableStateOf(false) }
        var expandedAromaQueijo by remember { mutableStateOf(false) }
        var expandedAparenciaAgradavelQueijo by remember { mutableStateOf(false) }
        var expandedFormatoRegularQueijo by remember { mutableStateOf(false) }
        var expandedSaborQueijo by remember { mutableStateOf(false) }

        var nomeQueijo by remember { mutableStateOf("") }
        var tipoQueijo by remember { mutableStateOf("MEIA_CURA") }
        var aromaQueijo by remember { mutableStateOf("MEDIO") }
        var aparenciaAgradavelQueijo by remember { mutableStateOf("MEDIO") }
        var formatoRegularQueijo by remember { mutableStateOf("MEDIO") }
        var saborQueijo by remember { mutableStateOf("MEDIO") }
        var precoQueijo by remember { mutableStateOf("0.0") }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Novo Queijo") },
            text = {
                Column {
                    OutlinedTextField(value = nomeQueijo,
                        onValueChange = {  nomeQueijo = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedTipoQueijo = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Tipo: ${TipoQueijo.valueOf(tipoQueijo).capitalize()}")
                        }
                        DropdownMenu(expanded = expandedTipoQueijo,
                            onDismissRequest = { expandedTipoQueijo = false },
                            scrollState = scrollTipoQueijo,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                TipoQueijo.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.capitalize()) },
                                        onClick = {
                                            tipoQueijo = it.name
                                            expandedTipoQueijo = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedAromaQueijo = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Aroma: ${AromaQueijo.valueOf(aromaQueijo).capitalize()}")
                        }
                        DropdownMenu(expanded = expandedAromaQueijo,
                            onDismissRequest = { expandedAromaQueijo = false },
                            scrollState = scrollAromaQueijo,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                AromaQueijo.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.capitalize()) },
                                        onClick = {
                                            aromaQueijo = it.name
                                            expandedAromaQueijo = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedAparenciaAgradavelQueijo = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Aparência Agradável: ${AparenciaAgradavel.valueOf(aparenciaAgradavelQueijo).capitalize()}")
                        }
                        DropdownMenu(expanded = expandedAparenciaAgradavelQueijo,
                            onDismissRequest = { expandedAparenciaAgradavelQueijo = false },
                            scrollState = scrollAparenciaAgradavelQueijo,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                AparenciaAgradavel.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.capitalize()) },
                                        onClick = {
                                            aparenciaAgradavelQueijo = it.name
                                            expandedAparenciaAgradavelQueijo = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedFormatoRegularQueijo = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Formato Regular: ${FormatoRegularQueijo.valueOf(formatoRegularQueijo).capitalize()}")
                        }
                        DropdownMenu(expanded = expandedFormatoRegularQueijo,
                            onDismissRequest = { expandedFormatoRegularQueijo = false },
                            scrollState = scrollFormatoRegularQueijo,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                FormatoRegularQueijo.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.capitalize()) },
                                        onClick = {
                                            formatoRegularQueijo = it.name
                                            expandedFormatoRegularQueijo = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedSaborQueijo = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Sabor: ${SaborQueijo.valueOf(saborQueijo).capitalize()}")
                        }
                        DropdownMenu(expanded = expandedSaborQueijo,
                            onDismissRequest = { expandedSaborQueijo = false },
                            scrollState = scrollSaborQueijo,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                SaborQueijo.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.capitalize()) },
                                        onClick = {
                                            saborQueijo = it.name
                                            expandedSaborQueijo = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = precoQueijo,
                        onValueChange = {  precoQueijo = it.trim() },
                        label = { Text("Valor") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        val idQueijo = UUID.randomUUID().toString()

                        val queijo = Queijo(
                            idQueijo,
                            nomeQueijo,
                            tipoQueijo,
                            aromaQueijo,
                            aparenciaAgradavelQueijo,
                            formatoRegularQueijo,
                            saborQueijo,
                            precoQueijo,
                            DataSource.getLoja()!!
                        )

                        if (queijoController.cadastrar(queijo)) {
                            snackbar.showSnackbar(
                                message = "Queijo criado",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbar.showSnackbar(
                                message = "Erro ao criar queijo",
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
    fun onEditarQueijo(scope: CoroutineScope, snackbar: SnackbarHostState, queijo: Queijo): Boolean {
        var state by remember { mutableStateOf(true) }

        val scrollTipoQueijo = rememberScrollState()
        val scrollAromaQueijo = rememberScrollState()
        val scrollAparenciaAgradavelQueijo = rememberScrollState()
        val scrollFormatoRegularQueijo = rememberScrollState()
        val scrollSaborQueijo = rememberScrollState()

        var expandedTipoQueijo by remember { mutableStateOf(false) }
        var expandedAromaQueijo by remember { mutableStateOf(false) }
        var expandedAparenciaAgradavelQueijo by remember { mutableStateOf(false) }
        var expandedFormatoRegularQueijo by remember { mutableStateOf(false) }
        var expandedSaborQueijo by remember { mutableStateOf(false) }

        var nomeQueijo by remember { mutableStateOf(queijo.nome.value) }
        var tipoQueijo by remember { mutableStateOf(queijo.tipo.name) }
        var aromaQueijo by remember { mutableStateOf(queijo.aroma.name) }
        var aparenciaAgradavelQueijo by remember { mutableStateOf(queijo.aparenciaAgradavel.name) }
        var formatoRegularQueijo by remember { mutableStateOf(queijo.formatoRegular.name) }
        var saborQueijo by remember { mutableStateOf(queijo.sabor.name) }
        var precoQueijo by remember { mutableStateOf(queijo.preco.value.toString()) }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Editar Queijo") },
            text = {
                Column {
                    OutlinedTextField(value = nomeQueijo,
                        onValueChange = {  nomeQueijo = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedTipoQueijo = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Tipo: ${TipoQueijo.valueOf(tipoQueijo).capitalize()}")
                        }
                        DropdownMenu(expanded = expandedTipoQueijo,
                            onDismissRequest = { expandedTipoQueijo = false },
                            scrollState = scrollTipoQueijo,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                TipoQueijo.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.capitalize()) },
                                        onClick = {
                                            tipoQueijo = it.name
                                            expandedTipoQueijo = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedAromaQueijo = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Aroma: ${AromaQueijo.valueOf(aromaQueijo).capitalize()}")
                        }
                        DropdownMenu(expanded = expandedAromaQueijo,
                            onDismissRequest = { expandedAromaQueijo = false },
                            scrollState = scrollAromaQueijo,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                AromaQueijo.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.capitalize()) },
                                        onClick = {
                                            aromaQueijo = it.name
                                            expandedAromaQueijo = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedAparenciaAgradavelQueijo = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Aparência Agradável: ${AparenciaAgradavel.valueOf(aparenciaAgradavelQueijo).capitalize()}")
                        }
                        DropdownMenu(expanded = expandedAparenciaAgradavelQueijo,
                            onDismissRequest = { expandedAparenciaAgradavelQueijo = false },
                            scrollState = scrollAparenciaAgradavelQueijo,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                AparenciaAgradavel.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.capitalize()) },
                                        onClick = {
                                            aparenciaAgradavelQueijo = it.name
                                            expandedAparenciaAgradavelQueijo = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedFormatoRegularQueijo = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Formato Regular: ${FormatoRegularQueijo.valueOf(formatoRegularQueijo).capitalize()}")
                        }
                        DropdownMenu(expanded = expandedFormatoRegularQueijo,
                            onDismissRequest = { expandedFormatoRegularQueijo = false },
                            scrollState = scrollFormatoRegularQueijo,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                FormatoRegularQueijo.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.capitalize()) },
                                        onClick = {
                                            formatoRegularQueijo = it.name
                                            expandedFormatoRegularQueijo = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { expandedSaborQueijo = true }, modifier = Modifier.fillMaxWidth()) {
                            Text("Sabor: ${SaborQueijo.valueOf(saborQueijo).capitalize()}")
                        }
                        DropdownMenu(expanded = expandedSaborQueijo,
                            onDismissRequest = { expandedSaborQueijo = false },
                            scrollState = scrollSaborQueijo,
                            modifier = Modifier.heightIn(100.dp, 120.dp).fillMaxWidth(0.75f),
                            content = {
                                SaborQueijo.entries.forEach {
                                    DropdownMenuItem(text = { Text(it.capitalize()) },
                                        onClick = {
                                            saborQueijo = it.name
                                            expandedSaborQueijo = false
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    OutlinedTextField(value = precoQueijo,
                        onValueChange = {  precoQueijo = it.trim() },
                        label = { Text("Valor") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        val queijo = Queijo(
                            queijo.id,
                            nomeQueijo,
                            tipoQueijo,
                            aromaQueijo,
                            aparenciaAgradavelQueijo,
                            formatoRegularQueijo,
                            saborQueijo,
                            precoQueijo,
                            DataSource.getLoja()!!
                        )

                        if (queijoController.alterar(queijo)) {
                            snackbar.showSnackbar(
                                message = "Queijo editado",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbar.showSnackbar(
                                message = "Erro ao editar queijo",
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
    fun onRemoverQueijo(scope: CoroutineScope, snackbar: SnackbarHostState, queijo: Queijo): Boolean {
        var state by remember { mutableStateOf(true) }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Remover Queijo") },
            text = { Text("Deseja remover o queijo?") },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        if (queijoController.deletar(queijo.id)) {
                            snackbar.showSnackbar(
                                message = "Queijo removido",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                        } else {
                            snackbar.showSnackbar(
                                message = "Erro ao remover queijo",
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
                            queijoController.alterar(lojaEditada)
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
    fun onRemoverLoja(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState, loja: Loja): Boolean {
        var state by remember { mutableStateOf(true) }

        AlertDialog(onDismissRequest = { state = false },
            title = { Text("Remover Loja") },
            text = { Text("Deseja remover a loja?") },
            confirmButton = {
                Button(onClick = {
                    state = false

                    scope.launch {
                        if (lojaController.deletar(loja.id)) {
                            queijoController.deletar(loja)
                            snackbar.showSnackbar(
                                message = "Produto removido",
                                actionLabel = "X",
                                duration = SnackbarDuration.Short
                            )
                            navigator.navigate("MainView")
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