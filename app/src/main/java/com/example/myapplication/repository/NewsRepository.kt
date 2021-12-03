package com.example.myapplication.repository

import com.example.myapplication.API.RetroInstance
import com.example.myapplication.db.ArticleDatabase
import com.example.myapplication.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getnews(countryCode: String, pageNumber: Int) =
        RetroInstance.api.getbreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetroInstance.api.searchNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)


}