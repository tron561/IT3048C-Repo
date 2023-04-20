package com.newsnow.api

data class JSONData(
    //Attributes captured from API directly
    val news: List<ArticleItem>,
    val page: Int,
    val status: String
)