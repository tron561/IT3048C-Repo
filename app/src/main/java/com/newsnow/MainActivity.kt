package com.newsnow

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
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
@Preview(name="Light Mode", showBackground=true)
@Preview(uiMode= Configuration.UI_MODE_NIGHT_YES, showBackground = true, name="Dark Mode")
@Composable
fun DefaultPreview() {
    NewsNowTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth()) {
            ArticleInfo("Android")
        }
    }
}