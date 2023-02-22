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
    var pubDate: Date,
    @PrimaryKey var id : Int = 0
) {
    override fun toString(): String {
        return title + " " + full_description
    }
}