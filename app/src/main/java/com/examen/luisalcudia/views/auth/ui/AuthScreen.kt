package com.examen.luisalcudia.views.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.examen.luisalcudia.views.auth.AuthViewModel
import com.examen.luisalcudia.views.auth.LoginState

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: (Boolean) -> Unit // Callback de login
) {
    var showLogin by remember { mutableStateOf(true) }
    val loginState by viewModel.loginState.observeAsState()

    when (loginState) {
        // Notificar que el login fue exitoso
        is LoginState.Success -> {
            onLoginSuccess(true)
        }
        is LoginState.Error -> {
            // Muestra el error de login
        }
        else -> {
            // Mostrar el formulario de login
        }
    }


    if (showLogin) {
        LoginScreen(viewModel = viewModel, onNavigateToRegister = { showLogin = false })
    } else {
        RegisterScreen(viewModel = viewModel, onNavigateToLogin = { showLogin = true })
    }
}