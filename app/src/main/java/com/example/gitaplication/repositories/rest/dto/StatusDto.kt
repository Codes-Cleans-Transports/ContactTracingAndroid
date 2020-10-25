package com.example.gitaplication.repositories.rest.dto

import com.example.gitaplication.firstScreen.Status
import com.google.gson.annotations.SerializedName

data class StatusDto(
    @SerializedName("status") val status: String,
    @SerializedName("safety") val safety: String
)

fun StatusDto.toStatus(): Status {
    return Status(safety = safety, status = status)
}