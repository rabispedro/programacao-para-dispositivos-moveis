package com.example.a0xc0ffee.view

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.CoroutineScope

interface View {
    val displayName: String
    val displayIcon: ImageVector
    val tintColor: Color

    @Composable
    fun View(scope: CoroutineScope, snackbar: SnackbarHostState)
}