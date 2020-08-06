package com.example.gitaplication.login.useCases

import com.example.gitaplication.account.AccountManager
import com.example.gitaplication.models.User
import com.multiplatform.util.Result
import com.multiplatform.util.UseCase

class AutoLoginUseCase(
    private val accountManager: AccountManager,
    private val loginUseCase: LoginUseCase
) : UseCase {

    suspend operator fun invoke(): Result<User> {

        if (!accountManager.hasSavedAccount()) {
            return Result.Error(IllegalStateException("No saved user"))
        }

        return loginUseCase(accountManager.getSavedAccount())
    }
}