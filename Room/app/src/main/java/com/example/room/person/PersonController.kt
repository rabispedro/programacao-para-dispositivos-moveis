package com.example.room.person

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class PersonController(private val service: PersonService) {
    lateinit var people: SnapshotStateSet<Person>
    lateinit var state: MutableState<String>

    @Composable
    fun SimplePersonView() {
        people = remember { mutableStateSetOf() }
        state = remember { mutableStateOf("Create") }

        var cpf by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            refreshList()
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.padding(top = 32.dp))
            Text("Person CRUD - IFTM")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = cpf,
                onValueChange = {
                    cpf = it.trim()
                    if (cpf.length > 11) {
                        cpf = cpf.substring(0, 11)
                    }
                },
                label = { Text("CPF") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (name.length > 50) {
                        name = name.substring(0, 50)
                    }
                },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val person = Person(cpf, name.trim())

                    when(state.value) {
                        "Create" -> {
                            service.insert(person) {
                                refreshList()
                            }
                        }
                        "Update" -> {
                            service.update(person)
                            refreshList()
                            state = mutableStateOf("Create")
                        }
                    }

                    cpf = ""
                    name = ""
                }
            ) {
                Text(state.value)
            }
            Spacer(modifier = Modifier.height(16.dp))
            CardPersonView()
        }
    }

    @Composable
    fun CardPersonView() {
        LaunchedEffect(Unit) {
            refreshList()
        }

        Text("People")
        LazyColumn {
            items(people.toList()) { person ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = {
//                            UpdatePersonView(person)
                                state = mutableStateOf("Update")
                                refreshList()
                        }
                    ) {
                        Text("${person.cpf} - ${person.name}")
                    }
                    Button(
                        onClick = {
                            service.delete(person)
                            refreshList()
                        },
                    ) {
                        Text("X")
                    }
                }
            }
        }
    }

    @Composable
    fun UpdatePersonView(person: Person) {
        Column {

        }
    }

    private fun refreshList() {
        service.findAll {
            people.clear()
            people.addAll(it)
        }
    }
}