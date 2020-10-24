package com.example.gitaplication.repositories.rest

import com.example.gitaplication.repositories.rest.dto.StatusDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CoronaService
{
    @POST("report_positive")
    suspend fun submitPositive(@Query("self") mac: String)

    @POST("report")
    suspend fun submitData(@Query("self") mac: String, @Query("contacts") contacts: Array<String>)

    @GET("users/{mac}")
    suspend fun scan(@Path("mac") mac: String): Response<StatusDto>
}