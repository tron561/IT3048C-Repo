package com.newsnow

import com.newsnow.api.JSONData
import retrofit2.http.GET

const val apiKey: String = BuildConfig.API_KEY

interface APIRequest {

    //API Key is exclusive to account hedgesaj@mail.uc.edu
        @GET("/v1/latest-news?language=en&apiKey=$apiKey")
        suspend fun getNews() : JSONData
}