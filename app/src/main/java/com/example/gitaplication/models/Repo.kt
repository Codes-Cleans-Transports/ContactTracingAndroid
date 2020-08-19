package com.example.gitaplication.models

import com.example.gitaplication.repositories.table.RepoEntity

data class Repo(
    val name: String,
    val description: String,
    val watchersCount: Int,
    val forksCount: Int
)

fun Repo.toDbRepo(username: String) =
    RepoEntity(
        name = name,
        userUsername = username,
        description = description,
        watchersCount = watchersCount,
        forksCount = forksCount
    )