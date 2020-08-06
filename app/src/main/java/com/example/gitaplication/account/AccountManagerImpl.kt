package com.example.gitaplication.account

import android.content.SharedPreferences

class AccountManagerImpl(
    private val sharedPreferences: SharedPreferences
) : AccountManager {

    override fun hasSavedAccount(): Boolean {
        return sharedPreferences.contains(PREFS_KEY)
    }

    override fun saveAccount(username: String) {
        sharedPreferences.edit().putString(PREFS_KEY, username).apply()
    }

    override fun getSavedAccount(): String {
        return sharedPreferences.getString(PREFS_KEY, null) ?: ""
    }

    override fun deleteSavedAccount() {
        sharedPreferences.edit().remove(PREFS_KEY).apply()
    }

    companion object {
        private const val PREFS_KEY = "username"
    }
}