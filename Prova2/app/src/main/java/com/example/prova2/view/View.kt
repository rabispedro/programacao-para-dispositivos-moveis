package com.example.prova2.view

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.CoroutineScope

interface View {
    val displayName: String
    val displayIcon: ImageVector

    @Composable
    fun View(scope: CoroutineScope, snackbar: SnackbarHostState)
}
