package com.example.gitaplication.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "follow_join",
    primaryKeys = ["follower_login", "followed_login"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["login"],
        childColumns = ["follower_login"],
        onUpdate = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["login"],
            childColumns = ["followed_login"],
            onUpdate = ForeignKey.CASCADE
        )]
)
data class FollowJoinEntity(
    @ColumnInfo(name = "follower_login") val followerLogin: String,
    @ColumnInfo(name = "followed_login", index = true) val followedLogin: String
)