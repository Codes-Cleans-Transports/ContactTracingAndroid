package com.example.gitaplication.mocks

import com.example.gitaplication.account.AccountManager

class AuthenticatorMock : AccountManager {

    override fun hasSavedAccount(): Boolean =true

    override fun saveAccount(username: String) {}

    override fun getSavedAccount(): String= "test"

    override fun deleteSavedAccount() { }
}