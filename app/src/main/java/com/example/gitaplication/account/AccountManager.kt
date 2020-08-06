package com.example.gitaplication.account

interface AccountManager {

    fun hasSavedAccount(): Boolean

    fun saveAccount(username: String)

    fun getSavedAccount(): String

    fun deleteSavedAccount()
}