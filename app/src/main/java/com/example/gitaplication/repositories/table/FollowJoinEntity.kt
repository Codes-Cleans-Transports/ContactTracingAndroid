package com.example.gitaplication.repositories.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "follow_join",
    primaryKeys = ["follower_username", "followed_username"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["username"],
        childColumns = ["follower_username"],
        onUpdate = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["followed_username"],
            onUpdate = ForeignKey.CASCADE
        )]
)
data class FollowJoinEntity(
    @ColumnInfo(name = "follower_username") val followerUsername: String,
    @ColumnInfo(name = "followed_username", index = true) val followedUsername: String
)