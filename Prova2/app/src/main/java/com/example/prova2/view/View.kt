package com.example.prova2.view

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

interface View {
    @SuppressLint("NotConstructor")
    @Composable
    fun View(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState)

    @Composable
    fun ActionButton(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState)

    @Composable
    fun TopBar(navigator: NavHostController, scope: CoroutineScope, snackbar: SnackbarHostState)
}
