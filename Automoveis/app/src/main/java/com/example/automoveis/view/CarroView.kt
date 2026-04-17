package com.example.automoveis.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.automoveis.model.Carro
import com.example.automoveis.ui.theme.ButtonThemedColors
import com.example.automoveis.ui.theme.CheckboxThemedColors
import com.example.automoveis.ui.theme.PrimaryColor
import com.example.automoveis.ui.theme.TextFieldThemedColors
import com.example.automoveis.viewmodel.CarroViewModel

class CarroView(val viewModel: CarroViewModel) : StepView {
    @Composable
    override fun Show() {
        var modelo by rememberSaveable { mutableStateOf("") }
        var ano by rememberSaveable { mutableStateOf("") }
        var potencia by rememberSaveable { mutableStateOf("") }
        var fabricante by rememberSaveable() { mutableStateOf("") }
        var temQuatroPortas by rememberSaveable { mutableStateOf(false) }

        Column {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = modelo,
                onValueChange = {
                    modelo = it.trim()
                    if (modelo.length > 20) {
                        modelo = modelo.substring(0, 20)
                    }
                },
                label = { Text("Modelo") },
                placeholder = { Text("Corsa") },
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = ano,
                onValueChange = {
                    ano = it.trim()
                    if (ano.length > 4) {
                        ano = ano.substring(0, 4)
                    }
                },
                label = { Text("Ano") },
                placeholder = { Text("1998") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = potencia,
                onValueChange = {
                    potencia = it.trim()

                    if (potencia.length > 7) {
                        potencia = potencia.substring(0, 7)
                    }
                },
                label = { Text("Potência (CV)") },
                placeholder = { Text("65.4") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            OutlinedTextField(
                value = fabricante,
                onValueChange = {
                    fabricante = it.trim()
                    if (fabricante.length > 20) {
                        fabricante = fabricante.substring(0, 20)
                    }
                },
                label = { Text("Fabricante") },
                placeholder = { Text("Chevrolet") },
                colors = TextFieldThemedColors,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text("Tem quatro portas?", color = PrimaryColor)
            Checkbox(
                checked = temQuatroPortas,
                onCheckedChange = { temQuatroPortas = it },
                colors = CheckboxThemedColors
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Button(
                content = { Text("Criar um novo Carro") },
                colors = ButtonThemedColors,
                onClick = {
                    if (modelo.isNotEmpty() && ano.isNotEmpty() && potencia.isNotEmpty() && fabricante.isNotEmpty()) {
                        val automovel = Carro(
                            modelo,
                            ano.toInt(),
                            potencia.toFloat(),
                            fabricante,
                            temQuatroPortas
                        )

                        Log.d("debug", automovel.toString())

                        viewModel.cria(automovel)
                    }

                    modelo = ""
                    ano = ""
                    potencia = ""
                    fabricante = ""
                    temQuatroPortas = false
                },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}
