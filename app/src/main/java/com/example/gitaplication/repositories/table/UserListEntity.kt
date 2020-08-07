package com.example.gitaplication.repositories.table

import androidx.room.ColumnInfo
import com.example.gitaplication.models.UserList


data class UserListItemEntity(
    @ColumnInfo(name = "login") val username: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String
)

fun UserListItemEntity.toUserListItem(): UserList = UserList(username = username, avatarUrl = avatarUrl)