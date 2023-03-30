package com.newsnow.dao

import android.os.Build
import com.newsnow.BuildConfig
import com.newsnow.dto.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IArticleDAO {
    // API key is exclusive to Francesca
    @GET("api/1/news")
    fun getAllArticles(@Query("apikey") apiKey: String = BuildConfig.API_KEY): Call<ArrayList<Article>>
}
