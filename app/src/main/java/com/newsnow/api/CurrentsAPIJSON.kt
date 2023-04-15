package com.newsnow.api

data class CurrentsAPIJSON(
    val news: List<New>,
    val page: Int,
    val status: String
)