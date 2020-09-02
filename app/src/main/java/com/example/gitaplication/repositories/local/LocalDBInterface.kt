package com.example.gitaplication.repositories.local

import com.example.gitaplication.models.Repo
import com.example.gitaplication.models.User
import com.example.gitaplication.models.UserList

interface LocalDBInterface { // TODO review2 DONE: What does LocalInterface mean? Find a more descriptive name

    suspend fun insertUser(user: User)

    suspend fun insertUserListItems(users: List<UserList>)

    suspend fun insertFollowers(username: String, followers: List<UserList>)

    suspend fun insertFollowing(username: String, following: List<UserList>)

    suspend fun insertRepos(username: String, repos: List<Repo>)

    suspend fun deleteRepos(username: String)

    suspend fun deleteFollowing(username: String)

    suspend fun deleteFollowers(username: String)
}