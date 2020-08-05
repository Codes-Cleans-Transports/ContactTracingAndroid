package com.example.gitaplication.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.gitaplication.models.Data

@Entity(
    tableName = "repos",
    primaryKeys = ["name", "user_login"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["login"],
        childColumns = ["user_login"],
        onUpdate = CASCADE
    )]
)
data class RepoEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "user_login", index = true) val userLogin: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "watchers_count") val watchersCount: Int,
    @ColumnInfo(name = "forks_count") val forksCount: Int
)

fun RepoEntity.toRepo(): Data =
    Data(
        name = name,
        description = description,
        watchersCount = watchersCount,
        forksCount = forksCount
    )