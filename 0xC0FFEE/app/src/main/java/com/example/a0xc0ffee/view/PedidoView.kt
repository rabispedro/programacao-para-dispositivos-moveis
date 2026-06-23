package com.example.a0xc0ffee.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a0xc0ffee.controller.PedidoController
import com.example.a0xc0ffee.ui.theme.AccentColor
import com.example.a0xc0ffee.ui.theme.icon.ReceiptIcon
import kotlinx.coroutines.CoroutineScope

class PedidoView(val controller: PedidoController): View {
    override val displayName = "Pedido"
    override val displayIcon = ReceiptIcon
    override val tintColor = Color(0xFF8B4513)

    @Composable
    override fun View(scope: CoroutineScope, snackbar: SnackbarHostState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pedidos",
                color = AccentColor,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp, 8.dp)
            )
        }
    }
}