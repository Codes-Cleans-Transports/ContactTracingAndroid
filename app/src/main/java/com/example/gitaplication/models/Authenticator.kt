package com.example.gitaplication.models

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class AccAuthenticator(app: Application){

    private val sharedPrefs: SharedPreferences =
        app.getSharedPreferences("GitHubConnections", Context.MODE_PRIVATE)

    fun logUserIn(login: String) {
        with(sharedPrefs.edit()) {
            putString("login", login)
            apply()
        }
    }

    fun logOut() {
        with(sharedPrefs.edit()) {
            remove("login")
            apply()
        }
    }

    fun isUserLoggedIn(): Boolean = sharedPrefs.getString("login", null) != null

    fun isCurrentlyLoggedIn(login: String): Boolean = getCurrentlyLoggedIn() == login

    fun getCurrentlyLoggedIn(): String? = sharedPrefs.getString("login", null)
}