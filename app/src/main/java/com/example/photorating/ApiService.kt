package com.example.photorating

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users/{username}/follow")
    fun toggleFollow(@Path("username") username: String): Call<FollowResponse>
}

data class FollowResponse(
    val message: String,
    val isFollowing: Boolean
)

object ApiClient {
    private const val BASE_URL = "YOUR_API_BASE_URL"

    fun create(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}