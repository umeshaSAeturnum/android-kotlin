package com.example.myapplication.API

import com.example.myapplication.Constants.Constants.Companion.API_KEY
import com.example.myapplication.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("v2/top-headlines")
    suspend fun getbreakingNews(
        @Query("country")
        countryCode : String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey : String = API_KEY
    ): Response<NewsResponse>
//    fun getData(
//        @Query("country")
//        countryCode : String = "us",
//        @Query("page")
//        pageNumber: Int = 1,
//        @Query("apiKey")
//        apiKey : String = API_KEY
//    ): Call<NewsResponse>
//
}