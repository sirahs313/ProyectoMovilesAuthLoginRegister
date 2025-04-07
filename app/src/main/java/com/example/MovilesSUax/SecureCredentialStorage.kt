package com.example.MovilesSUax

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecureCredentialStorage {
    private const val PREFS_NAME = "secure_auth_prefs_v2"
    private const val KEY_EMAIL = "encrypted_email"
    private const val KEY_PASSWORD = "encrypted_password"

    fun saveCredentials(context: Context, email: String, password: String) {
        try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            val sharedPreferences = EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            with(sharedPreferences.edit()) {
                putString(KEY_EMAIL, email)
                putString(KEY_PASSWORD, password)
                apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getCredentials(context: Context): Pair<String, String>? {
        return try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            val sharedPreferences = EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            val email = sharedPreferences.getString(KEY_EMAIL, null)
            val password = sharedPreferences.getString(KEY_PASSWORD, null)

            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                Pair(email, password)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun clearCredentials(context: Context) {
        try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            val sharedPreferences = EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            with(sharedPreferences.edit()) {
                remove(KEY_EMAIL)
                remove(KEY_PASSWORD)
                apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}