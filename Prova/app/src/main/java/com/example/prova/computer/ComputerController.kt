package com.example.prova.computer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.prova.shared.SimpleFormState
import com.example.prova.shared.SimpleFormView
import com.example.prova.ui.theme.ButtonThemedColors
import com.example.prova.ui.theme.PrimaryColor
import com.example.prova.ui.theme.TextColor
import com.example.prova.ui.theme.TextFieldThemedColors
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ComputerController(private val computerService: ComputerService) : SimpleFormView {
    @OptIn(ExperimentalUuidApi::class)
    @Composable
    override fun SimpleView() {
        var id by remember { mutableStateOf<String?>(null) }
        var model by remember { mutableStateOf("") }
        var memory by remember { mutableStateOf("") }
        var price by remember { mutableStateOf("") }
        var clientCpf by remember { mutableStateOf("") }

        val computers = remember { mutableStateSetOf<ComputerByClient>() }

        var state by remember { mutableStateOf(SimpleFormState.Create) }

        LaunchedEffect(Unit) {
            computerService.findAllByClient {
                computers.clear()
                computers.addAll(it)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 30.dp)
        ) {
            Text("Computer Form", color = PrimaryColor)
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = model,
                onValueChange = { model = it },
                label = { Text("Model") },
                placeholder = { Text("DoLL") },
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = memory,
                onValueChange = {  memory = it },
                label = { Text("Memory") },
                placeholder = { Text("16.00") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = price,
                onValueChange = {  price = it },
                label = { Text("Price") },
                placeholder = { Text("50.00") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = clientCpf,
                onValueChange = { clientCpf = it },
                label = { Text("Client's CPF") },
                placeholder = { Text("11122233344") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    content = { Text(state.name) },
                    colors = ButtonThemedColors,
                    onClick = {
                        when(state) {
                            SimpleFormState.Create ->  {
                                id = Uuid.random().toString().substring(0, 8)
                                val computer = Computer(id!!, model, memory.toDouble(), price.toDouble(), clientCpf)
                                computerService.insert(computer)
                            }
                            SimpleFormState.Update -> {
                                val computer = Computer(id!!, model, memory.toDouble(), price.toDouble(), clientCpf)
                                computerService.update(computer)
                                state = SimpleFormState.Create
                            }
                        }

                        computerService.findAllByClient {
                            computers.clear()
                            computers.addAll(it)
                        }

                        id = null
                        model = ""
                        memory = ""
                        price = ""
                        clientCpf = ""
                    }
                )
                Button(
                    content = { Text("Reset") },
                    colors = ButtonThemedColors,
                    onClick = {
                        state = SimpleFormState.Create

                        computerService.findAllByClient {
                            computers.clear()
                            computers.addAll(it)
                        }

                        id = null
                        model = ""
                        memory = ""
                        price = ""
                        clientCpf = ""
                    }
                )
            }
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(computers.toList()) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(PrimaryColor)
                        .border(color = TextColor, width = 1.dp)
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {
                                state = SimpleFormState.Update
                                id = it.computer.id
                                model = it.computer.model
                                price = it.computer.price.toString()
                                clientCpf = it.computer.clientCpf
                            },
                            content = { Text(it.toString(), color = TextColor, fontWeight = FontWeight.Bold) },
                            modifier = Modifier.width(280.dp))
                        IconButton(
                            onClick = { computerService.delete(it.computer) },
                            content = { Text("X", color = TextColor) }
                        )
                    }
                }
            }
        }
    }
}
