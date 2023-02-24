package com.newsnow.service

import com.newsnow.RetrofitClientInstance
import com.newsnow.dao.IArticleDAO
import com.newsnow.dto.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

interface IArticleService {
    suspend fun fetchArticles() : List<Article>?
}

class ArticleService : IArticleService {

    override suspend fun fetchArticles() : List<Article>? {
        return withContext(Dispatchers.IO) {
            val service = RetrofitClientInstance.retrofitInstance?.create(IArticleDAO::class.java)
            val countries = async {service?.getAllArticles()}
            var result = countries.await()?.awaitResponse()?.body()
            return@withContext result
        }
    }
}