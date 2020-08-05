package com.example.gitaplication.models

import com.example.gitaplication.table.FollowJoinEntity
import com.example.gitaplication.table.UserEntity

data class UserList(val username: String, val avatarUrl: String)

fun UserList.toDbUser(): UserEntity =
    UserEntity(login = username, avatarUrl = avatarUrl)

fun UserList.toFollower(followedLogin: String) =
    FollowJoinEntity(followerLogin = username, followedLogin = followedLogin)

fun UserList.toFollowed(followerLogin: String) =
    FollowJoinEntity(followerLogin = followerLogin, followedLogin = username)