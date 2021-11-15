package com.example.myapplication.repository

import com.example.myapplication.API.RetroInstance

class NewsRepository {
    suspend fun getnews(countryCode : String, pageNumber: Int) = RetroInstance.api.getbreakingNews(countryCode, pageNumber)
}