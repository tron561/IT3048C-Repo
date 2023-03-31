package com.newsnow.dto

import androidx.room.PrimaryKey
import androidx.room.Entity
@Entity(tableName="articles")
data class Article(
    var title : String = "",
    var link : String = "",
    var creator : String = "",
    var fullDescription : String = "",
    var pubDate: String = "",
    @PrimaryKey var id : Int = 0
) {
    // returns article title and full description
    override fun toString(): String {
        return title + " " + fullDescription
    }
}
