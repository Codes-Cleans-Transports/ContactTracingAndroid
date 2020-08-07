package com.example.gitaplication.repositories.rest.dto

import com.example.gitaplication.models.UserList
import com.google.gson.annotations.SerializedName

class UserListItemDto(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatarUrl: String
)

fun UserListItemDto.toUserListItem(): UserList =
    UserList(
        username = username,
        avatarUrl = avatarUrl
    )