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

        return try{
            local.getUser(username)
        }catch (e: IllegalStateException){
            updateUserData(username)
            local.getUser(username)
        }
    }

    override suspend fun getRepos(username: String): List<Repo> {
        return try{
            local.getRepos(username)
        }catch (e: IllegalStateException){
            updateRepos(username)
            local.getRepos(username)
        }
    }

    override suspend fun getFollowers(username: String): List<UserList> {
        return try{
            local.getFollowers(username)
        }catch (e: IllegalStateException){
            updateFollowers(username)
            local.getFollowers(username)
        }
    }

    override suspend fun getFollowing(username: String): List<UserList>{
        return try{
            local.getFollowing(username)
        }catch (e: IllegalStateException){
            updateFollowing(username)
            local.getFollowing(username)
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
