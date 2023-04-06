package com.newsnow.service

import com.newsnow.RetrofitClientInstance
import com.newsnow.dao.IArticleDAO
import com.newsnow.dto.Article
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// TODO: How to parse api key securely within a val base_url
// full url: "https://newsdata.io/api/1/news?apikey=pub_17584a2c69bac6e81deb6654731301f7095b1&q=pegasus&language=en"
private const val BASE_URL = "https://newsdata.io/api/1/"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface IArticleService {
    @GET("news")
    suspend fun fetchArticles() : List<Article>?
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MarsApi {
    val retrofitService: ArticleService by lazy { retrofit.create(ArticleService::class.java) }
}

class ArticleService : IArticleService {

    override suspend fun fetchArticles() : List<Article>? {
        return withContext(Dispatchers.IO) {
            val service = RetrofitClientInstance.retrofitInstance?.create(IArticleDAO::class.java)
            val articles = async {service?.getAllArticles()}
            var result = articles.await()?.awaitResponse()?.body()
            return@withContext result
        }
    }
}
