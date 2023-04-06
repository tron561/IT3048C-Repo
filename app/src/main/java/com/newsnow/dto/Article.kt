package com.newsnow.dto

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName="articles")
data class Article(
    var title: String,
    var link: String,
    var keywords: List<String>,
    var creator: List<String>,
    var videoUrl: String,
    var description: String,
    var content: String,
    var pubDate: String,
    var fullDescription: String,
    var imageUrl: String,
    var souruceId: String,
    var country: List<String>,
    var category: List<String>,
    var language: String,
    @PrimaryKey var id: String,
) {
    // returns article title and full description
    override fun toString(): String {
        return "$title $fullDescription"
    }
}
