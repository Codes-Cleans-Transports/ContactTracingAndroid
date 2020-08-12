package com.example.gitaplication.repositories

import com.example.gitaplication.models.Repo
import com.example.gitaplication.models.User
import com.example.gitaplication.models.UserList
import com.example.gitaplication.repositories.local.LocalRepository
import com.example.gitaplication.repositories.rest.RemoteRepository
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class CachingRepository(
    private val local: LocalRepository,
    private val remote: RemoteRepository
) : Repository {

    override suspend fun getUser(username: String): User {

        return try{
            local.getUser(username)
        }catch (e: IllegalArgumentException){
            updateUserData(username)
        }
    }

    override suspend fun getRepos(username: String): List<Repo> {
        return try{
            local.getRepos(username)
        }catch (e: IllegalArgumentException){
           updateRepos(username)
        }
    }

    override suspend fun getFollowers(username: String): List<UserList> {
        return try{
            local.getFollowers(username)
        }catch (e: IllegalArgumentException){
            updateFollowers(username)
        }
    }

    override suspend fun getFollowing(username: String): List<UserList>{
        return try{
            local.getFollowing(username)
        }catch (e: IllegalArgumentException){
           updateFollowing(username)
        }
    }


    private suspend fun updateUserData(username: String): User {
        try{
            val user: User = remote.getUser(username)
            local.insertUser(user)
            return user
        }catch(e: IllegalStateException){
            throw e
        }
    }

    private suspend fun updateRepos(username: String): List<Repo> {
        try{
            val repos: List<Repo> = remote.getRepos(username)
            local.insertRepos(username,repos)
            return repos
        }catch(e: IllegalStateException){
            throw e
        }
    }

    private suspend fun updateFollowers(username: String): List<UserList> {
        try{
            val followers: List<UserList> = remote.getFollowers(username)
            local.insertUserListItems(followers)
            local.insertFollowers(username, followers)
            return followers
        }catch(e: IllegalStateException){
            throw e
        }
    }

    private suspend fun updateFollowing(username: String): List<UserList> {
        try {
            val following: List<UserList> = remote.getFollowing(username)
            local.insertUserListItems(following)
            local.insertFollowers(username, following)
            return following
        } catch (e: IllegalStateException) {
            throw e
        }
    }
}
