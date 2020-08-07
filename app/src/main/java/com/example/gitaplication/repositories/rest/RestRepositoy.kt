package com.example.gitaplication.repositories.rest

import com.example.gitaplication.repositories.rest.dto.toRepo
import com.example.gitaplication.repositories.rest.dto.toUser
import com.example.gitaplication.repositories.rest.dto.toUserListItem
import com.example.gitaplication.models.Repo
import com.example.gitaplication.models.User
import com.example.gitaplication.models.UserList
import com.example.gitaplication.repositories.Repository

class RestRepository(
    private val restService: GitHubService
) : RemoteRepository {

    override suspend fun getUser(username: String): User {
        val response = restService.getUser(username)

        return if (response.isSuccessful) {

            val data = response.body() ?: throw IllegalStateException("Invalid user data")

            data.toUser()
        } else {
            throw IllegalStateException(response.errorBody()?.toString() ?: "")
        }
    }

    @Throws(Exception::class)
    override suspend fun getRepos(username: String): List<Repo> {
        val response = restService.getRepos(username)

        return if (response.isSuccessful) {
            val data = response.body() ?: throw IllegalStateException("Invalid user data")

            data.map { it.toRepo() }
        } else {
            throw IllegalStateException(response.errorBody()?.toString() ?: "")
        }
    }

    @Throws(Exception::class)
    override suspend fun getFollowers(username: String): List<UserList> {
        val response = restService.getFollowers(username)

        return if (response.isSuccessful) {
            val data = response.body() ?: throw IllegalStateException("Invalid user data")

            data.map { it.toUserListItem() }
        } else {
            throw IllegalStateException(response.errorBody()?.toString() ?: "")
        }
    }

    @Throws(Exception::class)
    override suspend fun getFollowing(username: String): List<UserList> {
        val response = restService.getFollowing(username)

        return if (response.isSuccessful) {
            val data = response.body() ?: throw IllegalStateException("Invalid user data")

            data.map { it.toUserListItem() }
        } else {
            throw IllegalStateException(response.errorBody()?.toString() ?: "")
        }
    }
}