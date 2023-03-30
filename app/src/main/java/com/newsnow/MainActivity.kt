package com.newsnow

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.newsnow.dto.Article
import com.newsnow.ui.theme.NewsNowTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Class represents the main activity for the NewsNow app and sets up the UI layout and theme
 */
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel<MainViewModel>()
    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO: parse in article object from API and put in FireStore database?
            // viewModel.loadNewArticle()
            viewModel.fetchArticles()
            val articles by viewModel.articles.observeAsState(initial = emptyList())
            val loading by viewModel.loading.observeAsState(initial = false)
            val error by viewModel.error.observeAsState(initial = null)

            NewsNowTheme {
                // surface container background color
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NavMenu("NewsNow")
                    if (loading) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else if (error != null) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Error: ${error!!.message}",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else {
                        ArticleInfo(articleList = articles)
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
                IconButton(onClick = { signIn() }) {
                    Icon(Icons.Default.Person, "Navigation")
                }
            }
        )
    }

    /**
     * Populates page with article data
     */
    @Composable
    fun ArticleInfo(articleList: List<Article>) {
        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
            for (article in articleList) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 72.dp, bottom = 8.dp),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = article.fullDescription,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "${article.creator} | ${article.pubDate}",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Source: ${article.link}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = article.content,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )
            }
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
                //ArticleInfo(articleList = articles)
            }
        }
    }

    private fun signIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()

        )
        val signinIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signinIntent)
    }

    private val signInLauncher = registerForActivityResult (
        FirebaseAuthUIActivityResultContract()
    ) {
            res -> this.signInResult(res)
    }


    private fun signInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            firebaseUser = FirebaseAuth.getInstance().currentUser
        } else {
            Log.e("MainActivity.kt", "Error logging in " + response?.error?.errorCode)

        }
    }
}




