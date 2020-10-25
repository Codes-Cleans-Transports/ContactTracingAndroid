package com.example.gitaplication.repositories.rest

import com.example.gitaplication.repositories.rest.dto.StatusDto
import retrofit2.Response
import retrofit2.http.*

interface CoronaService
{
    @PUT("users/{mac}")
    suspend fun submitPositive(@Path("mac") mac: String)

    @POST("report")
    suspend fun submitData(@Query("self") mac: String, @Query("contacts") contacts: Array<String>)

    @GET("users/{mac}")
    suspend fun loadStatus(@Path("mac") mac: String): Response<StatusDto>
}