package com.example.a0xc0ffee.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.a0xc0ffee.controller.ClienteController
import com.example.a0xc0ffee.model.Cliente
import com.example.a0xc0ffee.model.vo.CPF
import com.example.a0xc0ffee.model.vo.Endereco
import com.example.a0xc0ffee.model.vo.Instagram
import com.example.a0xc0ffee.model.vo.Nome
import com.example.a0xc0ffee.model.vo.Telefone
import com.example.a0xc0ffee.ui.theme.icon.StoreFrontIcon

class ClienteView(val controller: ClienteController): View {
    override val displayName = "Cliente"
    override val displayIcon = StoreFrontIcon
    override val tintColor = Color(0xFF8B4513)

    @Composable
    override fun View() {
        val clientes = remember { mutableListOf<Cliente>() }

        LaunchedEffect(Unit) {
            clientes.clear()
            clientes.addAll(controller.listar())
            Log.d("debug", "Clientes: ${clientes.size}")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { }

        LazyColumn() {
            items(clientes) {
                Row(modifier = Modifier.fillMaxWidth().background(Color.Red)) {
                    Text(it.toString())
                }
            }
        }
    }
}