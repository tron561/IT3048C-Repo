package com.newsnow

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.newsnow.ui.theme.NewsNowTheme

/**
 * Class represents the main activity for the NewsNow app and sets up the UI layout and theme
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsNowTheme {
                // surface container background color
                Surface(color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxWidth()){
                    NavMenu("NewsNow")
                    ArticleInfo()
                }
            }
        }
    }
}
/**
 * Creates a TopAppBar with the app title on the left and an account navigation on the right
 * @param appName name of the application
 */
@Composable
fun NavMenu(appName: String) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(appName)},
        backgroundColor = Color(android.graphics.Color.parseColor("#D9D9D9")),
        actions = {
            IconButton(onClick = {  }) {
                Icon(Icons.Default.Person, "Navigation")
            }
        }
    )
}
/**
 * Populates page with article data
 */
@Composable
fun ArticleInfo() {
    var articleTitle by remember { mutableStateOf("")}
    // sample text until we learn to put JSON data into the UI
    Column(modifier = Modifier
        .padding(start = 24.dp, end = 24.dp)) {
        Text(
            // title
            text = "Rihanna is BACK",
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 72.dp, bottom = 8.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            // sub title
            text = "International superstars performs at the Super Bowl Halftime Show.",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            // author and date
            text = "Jane Doe | 02/23/2023",
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            //where its found
            text = "Source: NY Times",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption
        )
        Text(
            //body text
            text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula" +
                    " eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient" +
                    " montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, " +
                    "pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, " +
                    "aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis " +
                    "vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus." +
                    " Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor " +
                    "eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, " +
                    "tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet." +
                    " Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. ",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
/**
 * Preview for layout in the IDE without AVD
 */
@Preview(name="Light Mode", showBackground=true)
@Preview(uiMode= Configuration.UI_MODE_NIGHT_YES, showBackground = true, name="Dark Mode")
@Composable
fun DefaultPreview() {
    NewsNowTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth()) {
            NavMenu("NewsNow")
            ArticleInfo()
        }
    }
}
