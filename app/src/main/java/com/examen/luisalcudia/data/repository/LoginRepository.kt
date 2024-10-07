package com.examen.luisalcudia.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Metodo para logear un usuario
    suspend fun loginUser(email: String, password: String): Result<String> {
        return try {
           // Iniciar sesión
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            val user = result.user   // Obtener el usuario autenticado

            if (user != null) {
                // Obtener el token del usuario
                val token = user.getIdToken(true).await().token
                if (token != null) {
                    Result.success(token) // Devolver el token
                } else {
                    Result.failure(Exception("No se pudo obtener el token"))
                }
            } else {
                Result.failure(Exception("No se pudo autenticar el usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // Metodo para registrar un usuario
    suspend fun registerUser(email: String, password: String): Result<String> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user   // Obtener el usuario autenticado

            if (user != null) {
                // Obtener el token del usuario
                val token = user.getIdToken(true).await().token
                if (token != null) {
                    Result.success(token) // Devolver el token
                } else {
                    Result.failure(Exception("No se pudo obtener el token"))
                }
            } else {
                Result.failure(Exception("No se pudo autenticar el usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Verificar si el token de firebase es valido
    suspend fun checkToken(token: String): Result<Boolean> {
        return try {
            val currentUser = firebaseAuth.currentUser

            if (currentUser != null && currentUser.getIdToken(false).await().token == token) {
                // El token es válido
                Result.success(true)
            } else {
                // El token es inválido o el usuario no está autenticado
                Result.failure(Exception("Token inválido o sesión expirada"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Metodo para obtener el usuario actual
    fun getCurrentUser() = firebaseAuth.currentUser

    // Metodo para cerrar sesión de firebase
    suspend fun logout() {
        firebaseAuth.signOut()
    }
}