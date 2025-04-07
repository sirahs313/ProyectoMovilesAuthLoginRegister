package com.example.MovilesSUax.api

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

data class RegisterRequest(val name: String, val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)
data class ApiResponse(val message: String)

interface ApiService {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<ApiResponse>

    @POST("register")
    fun signup(@Body registerRequest: RegisterRequest): Call<ApiResponse>

}

