package com.example.gitaplication.userDetails.useCases

import com.example.gitaplication.models.User
import com.example.gitaplication.repositories.Repository
import com.multiplatform.util.Result
import com.multiplatform.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchUserUseCase(
    private val repository: Repository
) : UseCase {

    suspend operator fun invoke(username: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            Result.Success(
                repository.getUser(username)
            )
        } catch (e: Exception) {
            Result.Error<User>(e)
        }
    }
}
