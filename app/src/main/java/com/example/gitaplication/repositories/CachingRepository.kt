package com.example.gitaplication.repositories

import com.example.gitaplication.models.Repo
import com.example.gitaplication.models.User
import com.example.gitaplication.models.UserList
import com.example.gitaplication.repositories.local.LocalRepository
import com.example.gitaplication.repositories.rest.RemoteRepository
import java.lang.IllegalStateException

class CachingRepository(
    private val local: LocalRepository,
    private val remote: RemoteRepository
) : Repository {

    override suspend fun getUser(username: String): User {
            try{
                updateUserData(username)
                return local.getUser(username)
            }catch (e: IllegalStateException){
                throw e
            }
    }

    override suspend fun getRepos(username: String): List<Repo> {
            try{
                updateRepos(username)
                return local.getRepos(username)
            }catch (e: IllegalStateException){
                throw e
            }
    }

    override suspend fun getFollowers(username: String): List<UserList> {
        try{
            updateFollowers(username)
            return local.getFollowers(username)
        }catch (e: IllegalStateException){
            throw e
        }
    }

    override suspend fun getFollowing(username: String): List<UserList>{
        try{
            updateFollowing(username)
            return local.getFollowing(username)
        }catch (e: IllegalStateException){
            throw e
        }
    }


    private suspend fun updateUserData(username: String) {
        try{
            val user: User = remote.getUser(username)
            local.insertUser(user)
        }catch(e: IllegalStateException){
            throw e
        }
    }

    private suspend fun updateRepos(username: String) {
        try{
            val repos: List<Repo> = remote.getRepos(username)
            local.insertRepos(username,repos)
        }catch(e: IllegalStateException){
            throw e
        }
    }

    private suspend fun updateFollowers(username: String) {
        try{
            val followers: List<UserList> = remote.getFollowers(username)
            local.insertUserListItems(followers)
            local.insertFollowers(username, followers)
        }catch(e: IllegalStateException){
            throw e
        }
    }

    private suspend fun updateFollowing(username: String) {
        try {
            val following: List<UserList> = remote.getFollowing(username)
            local.insertUserListItems(following)
            local.insertFollowers(username, following)
        } catch (e: IllegalStateException) {
            throw e
        }
    }
}
