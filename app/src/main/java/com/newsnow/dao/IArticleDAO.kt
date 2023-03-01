package com.newsnow.dao

import android.os.Build
import com.newsnow.BuildConfig
import com.newsnow.dto.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IArticleDAO {
    // API key is exclusive to Francesca
    // TODO: How to have an encrypted api key?
    @GET("api/1/news")
    fun getArticle(@Query("apikey") apiKey: String = BuildConfig.API_KEY): Call<Article>
}
