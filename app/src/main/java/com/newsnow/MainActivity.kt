package com.newsnow

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.newsnow.dto.Article
import com.newsnow.ui.theme.Grey
import com.newsnow.ui.theme.NewsNowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsNowTheme {
                // surface container background color
                Surface(color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxWidth()){
                    ArticleInfo(
                        Article(
                            title = "Rihanna is BACK",
                            link = "https://example.com/rihanna",
                            creator = "Jane Doe",
                            fullDescription = "International superstar performs at the Super Bowl Halftime Show.",
                            pubDate = "2023-02-23",
                            id = 1
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleInfo(article: Article) {
    Column {
        Text(
            //where its found
            text = "from the NYTimes...",
            fontSize = 15.sp,
        )
        Text(
            // title
            text = article.title,
            fontSize = 50.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Text(
            // author and date
            text = "${article.creator} | ${article.pubDate}",
            fontSize = 17.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Text(
            // sub title
            text = article.full_description,
            fontSize = 23.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}