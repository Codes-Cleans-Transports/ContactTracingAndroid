package com.example.gitaplication.mocks

import com.example.gitaplication.models.Repo
import com.example.gitaplication.models.User
import com.example.gitaplication.models.UserList
import com.example.gitaplication.repositories.Repository
import com.multiplatform.util.Result

class RepositoryMock : Repository {
    override suspend fun getUser(username: String): User =
        if (username == testingUser.username) {
            testingUser
        } else {
         throw   Throwable("An error occurred while fetching user")
        }

    override suspend fun getRepos(username: String): List<Repo> =
        if (username == testingUser.username) {
            testingRepos
        } else {
          throw  Throwable("An error occurred while fetching repos")
        }

    override suspend fun getFollowers(username: String): List<UserList> =
        if (username == testingUser.username) {
            testingUserListItems
        } else {
           throw Throwable("An error occurred while fetching follow list")
        }

    override suspend fun getFollowing(username: String): List<UserList> =
        getFollowers(username)
}

