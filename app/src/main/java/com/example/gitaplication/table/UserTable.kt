package com.example.gitaplication.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitaplication.models.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "bio") val bio: String = "No Data",
    @ColumnInfo(name = "location") val location: String = "No Data",
    @ColumnInfo(name = "public_repos") val publicRepos: Int = 0,
    @ColumnInfo(name = "followers") val followers: Int = 0,
    @ColumnInfo(name = "following") val following: Int = 0,
    @ColumnInfo(name = "user_mod_date") val userModDate: Long =  0L,
    @ColumnInfo(name = "repos_mod_date") val reposModDate: Long =  0L,
    @ColumnInfo(name = "followers_mod_date") val followersModDate: Long =  0L,
    @ColumnInfo(name = "following_mod_date") val followingModDate: Long =  0L
)

fun UserEntity.toUser(): User =
    User(
        username = login,
        avatarUrl = avatarUrl,
        bio = bio,
        location = location,
        publicRepos = publicRepos,
        followers = followers,
        following = following
    )