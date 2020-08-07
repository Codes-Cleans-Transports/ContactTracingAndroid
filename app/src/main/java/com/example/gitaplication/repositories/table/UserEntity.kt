package com.example.gitaplication.repositories.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitaplication.models.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "bio") val bio: String = "No data",
    @ColumnInfo(name = "location") val location: String = "No data",
    @ColumnInfo(name = "public_repos") val publicRepos: Int = 0,
    @ColumnInfo(name = "followers") val followers: Int = 0,
    @ColumnInfo(name = "following") val following: Int = 0
)

fun UserEntity.toUser(): User =
    User(
        username = username,
        avatarUrl = avatarUrl,
        bio = bio,
        location = location,
        publicRepos = publicRepos,
        followers = followers,
        following = following
    )