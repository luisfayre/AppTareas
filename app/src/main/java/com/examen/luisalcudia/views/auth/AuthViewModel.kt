package com.examen.luisalcudia.views.auth

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.luisalcudia.data.repository.LoginRepository
import com.examen.luisalcudia.utils.SharePreferenceUtil
import kotlinx.coroutines.launch
/***
 * ViewModel de la autenticación
 * */
class AuthViewModel(
    application: Application
) : AndroidViewModel(application) {
    private var loginRepository: LoginRepository = LoginRepository()

    private val _loginState: MutableLiveData<LoginState<Boolean>> = MutableLiveData()

    val loginState: LiveData<LoginState<Boolean>>
        get() = _loginState

    // Metodo para iniciar sesion
    fun login(email: String, password: String) {
        Log.wtf(TAG, "login: $email $password")

        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = loginRepository.loginUser(email, password)

            _loginState.value = if (result.isSuccess) {
                val token = result.getOrNull() // Token del usuario

                val context = getApplication<Application>().applicationContext
                SharePreferenceUtil(context).saveUserToken(token ?: "")

                LoginState.Success(true)
            } else {
                LoginState.Error(result.exceptionOrNull()?.message ?: "Error al iniciar sesión")
            }

        }
    }

    // Método para registrar usuarios
    fun register(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = loginRepository.registerUser(email, password)
            _loginState.value = if (result.isSuccess) {

                val token = result.getOrNull() // Token del usuario

                val context = getApplication<Application>().applicationContext
                SharePreferenceUtil(context).saveUserToken(token ?: "")


                LoginState.Success(true)
            } else {
                LoginState.Error(result.exceptionOrNull()?.message ?: "Error al registrar usuario")
            }
        }
    }
    // Metodo para cerrar sesión
     fun logout(){
         viewModelScope.launch {
             loginRepository.logout()
         }
    }

    // Verificar si existe token guardado
    fun checkIfLoggedIn() {
        val context = getApplication<Application>().applicationContext
        val token = SharePreferenceUtil(context).getUserToken()

        if (!token.isNullOrEmpty()) {

            viewModelScope.launch {
                val result = loginRepository.checkToken(token)
                _loginState.value = if (result.isSuccess) {
                    Log.wtf("asdasd ESTALOGEADO ALV" ,"ASD")
                    LoginState.Success(true) // Token válido, redirigir a Home
                } else {
                    LoginState.Error("Token inválido, por favor inicie sesión nuevamente")
                }
            }
        } else {
            _loginState.value = LoginState.Idle // No hay token, redirigir a la pantalla de login
        }
    }

}

// Estados de la respuesta
sealed class LoginState<out T> {
    data class Success<out T>(val data: T) : LoginState<T>()
    data class Error(val error: String?) : LoginState<Nothing>()
    object Loading : LoginState<Nothing>()
    object Idle : LoginState<Nothing>() // Agrega este estado "idle"
}
