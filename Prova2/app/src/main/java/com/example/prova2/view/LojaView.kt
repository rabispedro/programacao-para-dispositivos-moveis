package com.example.prova2.view

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.example.prova2.controller.LojaController
import com.example.prova2.ui.theme.icon.StoreFrontIcon
import kotlinx.coroutines.CoroutineScope

class LojaView(val controller: LojaController) : View {
    override val displayName = "Loja"
    override val displayIcon = StoreFrontIcon

    @Composable
    override fun View(
        scope: CoroutineScope,
        snackbar: SnackbarHostState
    ) {

    }
}