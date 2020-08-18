package com.example.gitaplication.userDetails.useCases

import com.example.gitaplication.models.UserList
import com.example.gitaplication.repositories.Repository
import com.multiplatform.util.Result
import com.multiplatform.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchFollowingUseCase(
    private val repository: Repository
) : UseCase {

    suspend operator fun invoke(username: String): Result<List<UserList>> = withContext(Dispatchers.IO) {
        try {
            Result.Success(
                repository.getFollowing(username)
            )
        } catch (e: Exception) {
            Result.Error<List<UserList>>(e)
        }
    }
}