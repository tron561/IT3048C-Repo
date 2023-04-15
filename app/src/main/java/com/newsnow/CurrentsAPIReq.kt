package com.newsnow

import com.newsnow.api.CurrentsAPIJSON
import retrofit2.http.GET

interface CurrentsAPIReq {

    //API Key is exclusive to account hedgesaj@mail.uc.edu
        @GET("/v1/latest-news?language=en&apiKey=K4R1MTDCh9MMUtEGWVHJWl2b1YVQXnw_vk2RvxR4_m0rd1e8")
        suspend fun getNews() : CurrentsAPIJSON
}