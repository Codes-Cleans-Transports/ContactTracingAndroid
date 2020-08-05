package com.example.gitaplication.repositories

import com.example.gitaplication.dto.toRepo
import com.example.gitaplication.dto.toUser
import com.example.gitaplication.dto.toUserListItem
import com.example.gitaplication.models.Data
import com.example.gitaplication.models.User
import com.example.gitaplication.models.UserList
import com.example.gitaplication.services.GitHubService

class RestRepository(
    private val restService: GitHubService
) : Repository {

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
    override suspend fun getRepos(username: String): List<Data> {
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