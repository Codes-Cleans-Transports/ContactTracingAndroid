package com.example.gitaplication.models

import android.os.Parcelable
import com.example.gitaplication.repositories.table.UserEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val username: String,
    val avatarUrl: String,
    val bio: String,
    val location: String,
    val publicRepos: Int,
    val followers: Int,
    val following: Int
) : Parcelable

fun User.toDbUser(): UserEntity =
    UserEntity(
        username = username,
        avatarUrl = avatarUrl,
        bio = bio,
        location = location,
        publicRepos = publicRepos,
        followers = followers,
        following = following
    )