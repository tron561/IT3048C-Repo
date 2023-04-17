package com.newsnow.api

data class JSONData(
    val news: List<ArticleItem>,
    val page: Int,
    val status: String
)