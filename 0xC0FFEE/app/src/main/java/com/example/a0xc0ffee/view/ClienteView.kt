package com.example.a0xc0ffee.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.a0xc0ffee.controller.ClienteController
import com.example.a0xc0ffee.ui.theme.icon.StoreFrontIcon

class ClienteView(val controller: ClienteController): View {
    override val displayName = "Cliente"
    override val displayIcon = StoreFrontIcon
    override val tintColor = Color(0xFF8B4513)

    @Composable
    override fun View() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { }
    }
}