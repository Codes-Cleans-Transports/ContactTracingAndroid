package com.example.gitaplication.models

import com.example.gitaplication.repositories.table.FollowJoinEntity
import com.example.gitaplication.repositories.table.UserEntity


data class UserList(val username: String, val avatarUrl: String)

fun UserList.toDbUser(): UserEntity =
    UserEntity(username = username, avatarUrl = avatarUrl)

fun UserList.toFollower(followedUsername: String) =
    FollowJoinEntity(followerUsername = username, followedUsername = followedUsername)

fun UserList.toFollowed(followerUsername: String) =
    FollowJoinEntity(followerUsername = followerUsername, followedUsername = username)