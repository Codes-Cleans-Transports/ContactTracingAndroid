package com.example.gitaplication.login.useCases

import com.example.gitaplication.account.AccountManager
import com.example.gitaplication.models.User
import com.example.gitaplication.repositories.Repository
import com.multiplatform.util.Result
import com.multiplatform.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCase(
    private val repository: Repository,
    private val accountManager: AccountManager
) : UseCase {
    suspend operator fun invoke(username: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val user = repository.getUser(username)

            accountManager.saveAccount(username)

            Result.Success(user)
        } catch (e: Exception) {
            Result.Error<User>(e)
        }
    }
}