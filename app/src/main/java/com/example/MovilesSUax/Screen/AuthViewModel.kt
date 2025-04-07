package com.example.tercerparcial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.MovilesSUax.api.ApiResponse
import com.example.MovilesSUax.api.ApiService
import com.example.MovilesSUax.api.LoginRequest
import com.example.MovilesSUax.api.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(private val apiService: ApiService) : ViewModel() {
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    // Verificación de estado de autenticación (esto puede ser mejorado si tu API soporta token o persistencia)
    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        // Aquí puedes usar algún mecanismo para verificar si el usuario ya está logueado (por ejemplo, token)
        _authState.value = AuthState.Unauthenticated
    }

    // Función para iniciar sesión
    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        val loginRequest = LoginRequest(email, password)

        apiService.login(loginRequest).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    // En caso de éxito, puedes obtener algún token o información adicional
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _authState.value = AuthState.Error(t.message ?: "Unknown error")
            }
        })
    }

    fun signup(name: String, email: String, password: String) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("All fields are required")
            return
        }

        _authState.value = AuthState.Loading
        val registerRequest = RegisterRequest(name, email, password) // Cambiar a RegisterRequest

        apiService.signup(registerRequest).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _authState.value = AuthState.Error(t.message ?: "Unknown error")
            }
        })
    }


    // Función para cerrar sesión
    fun logout() {
        // Aquí puedes manejar el cierre de sesión, si tu API permite algo como un endpoint de logout
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    object Authenticated : AuthState()  // El usuario está autenticado
    object Unauthenticated : AuthState()  // El usuario no está autenticado
    object Loading : AuthState()  // El proceso está en curso
    data class Error(val message: String) : AuthState()  // Hubo un error en el proceso
}
