package com.example.gitaplication.repositories

import com.example.gitaplication.firstScreen.Status

interface Repository {

    @Throws(Exception::class)
    suspend fun submitPositive(mac: String)

    @Throws(Exception::class)
    suspend fun submitData(mac: String, contacts: Array<String>)

    @Throws(Exception::class)
    suspend fun loadStatus(mac: String): Status
}
