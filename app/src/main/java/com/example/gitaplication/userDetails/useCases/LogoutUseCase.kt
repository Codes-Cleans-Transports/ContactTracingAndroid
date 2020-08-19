package com.example.gitaplication.userDetails.useCases


import com.example.gitaplication.account.AccountManager
import com.multiplatform.util.UseCase

class LogoutUseCase(
    private val accountManager: AccountManager
) : UseCase {

    operator fun invoke() =
        accountManager.deleteSavedAccount()

}
