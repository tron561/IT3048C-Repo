package com.newsnow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.newsnow.ui.theme.NewsNowTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsNowTheme {
                // surface container background color
                Surface(color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxWidth()){
                    ArticleInfo("Android")
                }
            }
        }
    }
}
@Composable
fun ArticleInfo(name : String) {
    var articleName by remember { mutableStateOf("")}
    OutlinedTextField(
        value = articleName,
        onValueChange = {articleName = it},
        label = { Text(stringResource(R.string.articleName))}
        )
}