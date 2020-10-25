package com.example.gitaplication.repositories.rest

import com.example.gitaplication.repositories.rest.dto.StatusDto
import retrofit2.Response
import retrofit2.http.*

interface CoronaService {
    @PUT("users/{mac}")
    suspend fun submitPositive(@Path("mac") mac: String)

    @POST("contacts/{mac}/")
    suspend fun submitData(@Path("mac") mac: String, @Body contacts: Conatacts)

    @GET("users/{mac}")
    suspend fun loadStatus(@Path("mac") mac: String): Response<StatusDto>
}