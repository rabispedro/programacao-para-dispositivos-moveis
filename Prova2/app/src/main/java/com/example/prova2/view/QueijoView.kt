package com.example.prova2.view

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.example.prova2.controller.QueijoController
import com.example.prova2.ui.theme.icon.RoomServiceIcon
import kotlinx.coroutines.CoroutineScope

class QueijoView(val controller: QueijoController) : View {
    override val displayName = "Queijo"
    override val displayIcon = RoomServiceIcon

    @Composable
    override fun View(
        scope: CoroutineScope,
        snackbar: SnackbarHostState
    ) {

    }
}