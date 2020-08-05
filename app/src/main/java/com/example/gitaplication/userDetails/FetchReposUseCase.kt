package com.example.gitaplication.userDetails

import android.util.Log
import com.example.gitaplication.models.Data
import com.example.gitaplication.repositories.Repository
import com.multiplatform.util.Result
import com.multiplatform.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchReposUseCase(
    private val repository: Repository
) : UseCase {

    suspend operator fun invoke(username: String): Result<List<Data>> = withContext(Dispatchers.IO) {
        try {
            Result.Success(
                repository.getRepos(username)
                    .sortedWith(compareByDescending<Data> { it.forksCount }.thenByDescending { it.watchersCount })
            )
        } catch (e: Exception) {
            Result.Error<List<Data>>(e)
        }
    }
}
