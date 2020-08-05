package com.example.gitaplication.dto

import com.example.gitaplication.models.Data
import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("watchers_count") val watchersCount: Int,
    @SerializedName("forks_count") val forksCount: Int
)

fun RepoDto.toRepo(): Data =
    Data(
        name = name,
        description = description ?: "No Data",
        watchersCount = watchersCount,
        forksCount = forksCount
    )