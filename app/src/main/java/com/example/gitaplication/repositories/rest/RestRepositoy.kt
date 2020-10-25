package com.example.gitaplication.repositories.rest

import com.example.gitaplication.firstScreen.Status
import com.example.gitaplication.repositories.Repository
import com.example.gitaplication.repositories.rest.dto.toStatus

class RestRepository(
    private val restService: CoronaService
) : Repository {
    override suspend fun submitPositive(mac: String) {
        restService.submitPositive(mac)
    }

    override suspend fun submitData(mac: String, contacts: Array<String>) {
        try {
            restService.submitData(mac, Conatacts(macs = contacts))
        } catch (e: Throwable) {
        }
    }

    override suspend fun loadStatus(mac: String): Status {

        val response = restService.loadStatus(mac)

        return if (response.isSuccessful) {
            val data = response.body() ?: throw IllegalStateException("Invalid user data")

            data.toStatus()
        } else {
            throw IllegalStateException(response.errorBody()?.toString() ?: "")
        }
    }
}