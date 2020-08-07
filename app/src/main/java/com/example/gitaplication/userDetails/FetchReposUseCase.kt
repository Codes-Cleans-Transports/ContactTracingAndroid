package com.example.gitaplication.userDetails

import com.example.gitaplication.models.Repo
import com.example.gitaplication.repositories.Repository
import com.multiplatform.util.Result
import com.multiplatform.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchReposUseCase(
    private val repository: Repository
) : UseCase {

    suspend operator fun invoke(username: String): Result<List<Repo>> = withContext(Dispatchers.IO) {
        try {
            Result.Success(
                repository.getRepos(username)
                    .sortedWith(compareByDescending<Repo> { it.forksCount }.thenByDescending { it.watchersCount })
            )
        } catch (e: Exception) {
            Result.Error<List<Repo>>(e)
        }
    }
}
