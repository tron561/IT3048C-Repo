package com.newsnow.api

data class CurrentsAPIJSON(
    val news: List<ArticleItem>,
    val page: Int,
    val status: String
)