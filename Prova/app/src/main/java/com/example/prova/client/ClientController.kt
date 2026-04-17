package com.example.prova.client

import android.util.Log
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
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
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

class ClientController(private val clientService: ClientService) : SimpleFormView {
    @Composable
    override fun SimpleView() {
        var cpf by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }

        val clients = remember { mutableStateSetOf<Client>() }

        var state by remember { mutableStateOf(SimpleFormState.Create) }

        LaunchedEffect(Unit) {
            clientService.findAll {
                clients.clear()
                clients.addAll(it)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 30.dp)
        ) {
            Text("Client Form", color = PrimaryColor)
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = cpf,
                onValueChange = { cpf = it },
                label = { Text("CPF") },
                placeholder = { Text("11122233344") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = name,
                onValueChange = {  name = it },
                label = { Text("Name") },
                placeholder = { Text("John Doe") },
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("john@doe.com") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = {  phone = it },
                label = { Text("Phone") },
                placeholder = { Text("11233334444") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
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
                        val client = Client(cpf, name, email, phone)

                        when(state) {
                            SimpleFormState.Create -> {
                                clientService.insert(client)
                            }
                            SimpleFormState.Update -> {
                                clientService.update(client)
                                state = SimpleFormState.Create
                            }
                        }

                        clientService.findAll {
                            clients.clear()
                            clients.addAll(it)
                        }

                        cpf = ""
                        name = ""
                        email = ""
                        phone = ""
                    }
                )
                Button(
                    content = { Text("Reset") },
                    colors = ButtonThemedColors,
                    onClick = {
                        state = SimpleFormState.Create

                        clientService.findAll {
                            clients.clear()
                            clients.addAll(it)
                        }

                        cpf = ""
                        name = ""
                        email = ""
                        phone = ""
                    }
                )
            }
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(clients.toList()) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(PrimaryColor)
                        .border(color = TextColor, width = 1.dp)
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            shape = IconButtonDefaults.outlinedShape,
                            onClick = {
                                state = SimpleFormState.Update
                                cpf = it.cpf
                                name = it.name
                                email = it.email
                                phone = it.phone
                            },
                            content = { Text(it.toString(), color = TextColor, fontWeight = FontWeight.Bold) },
                            modifier = Modifier.width(280.dp))
                        IconButton(
                            onClick = { clientService.delete(it) },
                            content = { Text("X", color = TextColor) }
                        )
                    }
                }
            }
        }
    }
}