package com.newsnow.dao

import com.newsnow.dto.Article
import retrofit2.Call
import retrofit2.http.GET

interface IArticleDAO {

    @GET("/api/1/news?apikey=pub_17584a2c69bac6e81deb6654731301f7095b1")
    fun getAllArticles(): Call<ArrayList<Article>>
}