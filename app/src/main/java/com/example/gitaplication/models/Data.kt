package com.example.gitaplication.models

import com.example.gitaplication.table.RepoEntity

data class Data(
    val name: String,
    val description: String,
    val watchersCount: Int,
    val forksCount: Int
)

fun Data.toDbRepo(username: String) =
    RepoEntity(
        name = name,
        userLogin = username,
        description = description,
        watchersCount = watchersCount,
        forksCount = forksCount
    )