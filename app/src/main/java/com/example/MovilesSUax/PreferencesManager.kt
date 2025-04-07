package com.example.MovilesSUax



import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun clearToken() {
        val editor = sharedPreferences.edit()
        editor.remove("auth_token")
        editor.apply()
    }
}
