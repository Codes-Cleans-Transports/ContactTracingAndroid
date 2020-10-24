package com.example.gitaplication.repositories.rest.dto

import com.google.gson.annotations.SerializedName

data class StatusDto(
    @SerializedName("status") val status: String
)

fun StatusDto.toStatus(): String = status