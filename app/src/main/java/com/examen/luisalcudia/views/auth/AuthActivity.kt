package com.examen.luisalcudia.views.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.examen.luisalcudia.views.auth.ui.AuthScreen
import com.examen.luisalcudia.views.home.HomeActivity

/***
 * Actividad para visualizar el login y el registro  con jetpack compose
 * */
class AuthActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel.checkIfLoggedIn() // Validar si el usuario tiene un token guardado

        authViewModel.loginState.observe(this) { loginState ->
            when (loginState) {
                is LoginState.Success -> {
                    navigateToHome()
                }
                is LoginState.Error -> {
                    // El usuario no está logeado, continuar mostrando la pantalla de login
                    setContent {
                        AuthScreen(viewModel = authViewModel) { loginSuccess ->
                            if (loginSuccess) {
                                navigateToHome()
                            }
                        }
                    }
                }
                else -> {
                    // Mostrar pantalla de login por defecto
                    setContent {
                        AuthScreen(viewModel = authViewModel) { loginSuccess ->
                            if (loginSuccess) {
                                navigateToHome()
                            }
                        }
                    }
                }
            }
        }
    }

    // Navegar al usuario a home
    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}