package com.example.gitaplication.firstScreen.useCases

import com.example.gitaplication.repositories.Repository
import com.multiplatform.util.Result
import com.multiplatform.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadStatusUseCase(
    private val repository: Repository
) : UseCase {

    suspend operator fun invoke(mac: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val status = repository.scan(mac)

            Result.Success(status)

        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}