package com.example.gitaplication.repositories.rest

import com.example.gitaplication.repositories.Repository

class RestRepository(
    private val restService: CoronaService
) : Repository {
    override suspend fun submitPositive(mac: String) {
        restService.submitPositive(mac)
    }

    override suspend fun submitData(mac: String, contacts: Array<String>) {
        restService.submitData(mac,contacts)
    }

    override suspend fun scan(mac: String): String {

        val response = restService.scan(mac)

        return if (response.isSuccessful) {
            val data = response.body() ?: throw IllegalStateException("Invalid user data")

            data.status
        } else {
            throw IllegalStateException(response.errorBody()?.toString() ?: "")
        }
    }
}