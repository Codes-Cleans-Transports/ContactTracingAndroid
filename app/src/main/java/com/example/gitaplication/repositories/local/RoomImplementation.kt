package com.example.gitaplication.repositories.local

import com.example.gitaplication.models.*
import com.example.gitaplication.repositories.RepoError
import com.multiplatform.util.Result
import com.example.gitaplication.repositories.table.*
import java.lang.IllegalArgumentException
import java.util.*

class RoomImplementation(
    private val database: RoomDB
) : LocalRepository {

    override suspend fun getRepos(username: String): List<Repo> {
        val response: List<RepoEntity> = database.repoDao().getRepos(username)
        return response.map { repoDb -> repoDb.toRepo() }
    }

    override suspend fun getUser(username: String): User =
        database.userDao().getUser(username)?.toUser()
            ?: throw IllegalArgumentException("No such user")

    override suspend fun getFollowers(username: String): List<UserList> {
        val followers: List<UserListItemEntity> = database.userDao().getFollowers(username)
        return followers.map { itemDbDto -> itemDbDto.toUserListItem() }
    }

    override suspend fun getFollowing(username: String): List<UserList> {
        val following: List<UserListItemEntity> = database.userDao().getFollowing(username)
        return following.map { itemDbDto -> itemDbDto.toUserListItem() }
    }

    override suspend fun insertUser(user: User) {
        database.userDao().insertUser(user.toDbUser())
    }

    override suspend fun insertUserListItems(users: List<UserList>) {
        database.userDao().insertIncompleteUsers(users.map { it.toDbUser() })
    }

    override suspend fun insertFollowers(username: String, followers: List<UserList>) {
        database.userDao().insertFollowPair(followers.map { it.toFollower(username) })
    }

    override suspend fun insertFollowing(username: String, following: List<UserList>) {
        database.userDao().insertFollowPair(following.map { it.toFollowed(username) })
    }

    override suspend fun insertRepos(username: String, repos: List<Repo>) {
        database.repoDao().insertRepos(repos.map { it.toDbRepo(username) })
    }

    override suspend fun deleteRepos(username: String) {
        database.repoDao().deleteRepos(username)
    }

    override suspend fun deleteFollowing(username: String) {
        database.userDao().deleteFollowing(username)
    }

    override suspend fun deleteFollowers(username: String) {
        database.userDao().deleteFollowers(username)
    }

}