package com.newsnow.dto

import androidx.room.PrimaryKey
import androidx.room.Entity
import java.util.*

@Entity(tableName="articles")
data class Article(
    var title : String,
    var link : String,
    var creator : String,
    var full_description : String,
    var pubDate: String,
    @PrimaryKey var id : Int = 0
) {
    // returns article title and full description
    override fun toString(): String {
        return "Title: $title\n" +
                "Description: $full_description\n"
    }

    //Returns all parameters of an Article
    fun toStringAll(): String {
        return "Title: $title\n" +
                "Link: $link\n" +
                "Creator: $creator\n" +
                "Description: $full_description\n" +
                "Publication Date: $pubDate\n"
    }
}
