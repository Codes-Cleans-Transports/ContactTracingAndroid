package com.example.gitaplication.repositories.rest.dto

import com.example.gitaplication.models.User
import com.google.gson.annotations.SerializedName

class UserDto(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("bio") val bio: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("public_repos") val publicRepos: Int,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int
)

fun UserDto.toUser(): User =
    User(
        username = username,
        avatarUrl = avatarUrl,
        bio = bio ?: "No Data",
        location = location ?: "No Data",
        publicRepos = publicRepos,
        followers = followers,
        following = following
    )