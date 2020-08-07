package com.example.gitaplication.repositories

import com.example.gitaplication.models.Repo
import com.example.gitaplication.models.User
import com.example.gitaplication.models.UserList
import java.lang.Exception

interface Repository {

    @Throws(Exception::class)
    suspend fun getUser(username: String): User

    @Throws(Exception::class)
    suspend fun getRepos(username: String): List<Repo>

    @Throws(Exception::class)
    suspend fun getFollowers(username: String): List<UserList>

    @Throws(Exception::class)
    suspend fun getFollowing(username: String): List<UserList>

}
