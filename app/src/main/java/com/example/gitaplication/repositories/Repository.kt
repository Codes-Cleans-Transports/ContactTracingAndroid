package com.example.gitaplication.repositories

interface Repository {

    @Throws(Exception::class)
    suspend fun submitPositive(mac: String)

    @Throws(Exception::class)
    suspend fun submitData(mac: String, contacts: Array<String>)

    @Throws(Exception::class)
    suspend fun scan(mac: String): String
}
