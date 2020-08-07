package com.example.gitaplication.repositories.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.gitaplication.models.Repo

@Entity(
    tableName = "repos",
    primaryKeys = ["name", "user_username"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["username"],
        childColumns = ["user_username"],
        onUpdate = ForeignKey.CASCADE
    )]
)
data class RepoEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "user_username", index = true) val userUsername: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "watchers_count") val watchersCount: Int,
    @ColumnInfo(name = "forks_count") val forksCount: Int
)

fun RepoEntity.toRepo(): Repo =
    Repo(
        name = name,
        description = description,
        watchersCount = watchersCount,
        forksCount = forksCount
    )