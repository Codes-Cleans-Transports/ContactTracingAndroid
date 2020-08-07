package com.example.gitaplication.repositories.rest

import com.example.gitaplication.repositories.rest.dto.RepoDto
import com.example.gitaplication.repositories.rest.dto.UserDto
import com.example.gitaplication.repositories.rest.dto.UserListItemDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<UserDto>

    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): Response<List<RepoDto>>

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): Response<List<UserListItemDto>>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): Response<List<UserListItemDto>>

}