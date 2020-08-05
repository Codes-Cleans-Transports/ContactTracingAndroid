package com.example.gitaplication.repositories

sealed class RepoError(message: String): Error(message) {

    class HttpError(statusMessage: String) : RepoError(statusMessage)
    object UserNotFound : RepoError("User not found")
    object EmptyBody: RepoError("Empty response body")
}