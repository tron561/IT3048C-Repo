package com.newsnow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.newsnow.service.ArticleService
import com.newsnow.dto.Article
import kotlinx.coroutines.launch
import com.newsnow.dto.User


class MainViewModel : ViewModel()  {

    internal val NEW_ARTICLE = "New Article"
    var articles : MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
    var articleService : ArticleService = ArticleService()
    var user : User? = null

    private lateinit var firestore : FirebaseFirestore

    //TODO: init block below is causing issues for MVM unit tests
    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    //WIP -AH

    /**
     * Function listens for any changes to any other changes with user's Firebase DataBase Article documents
     * To be called in MainActivity if saving functionality is implemented for Articles
     */
    fun listenToArticles() {
        user?.let {
                user ->
            firestore.collection("users").document(user.uid).collection("articles").addSnapshotListener {
                    snapshot, e ->
                // handle the error if there is one, and then return
                if (e != null) {
                    Log.w("Listen failed", e)
                    return@addSnapshotListener
                }
                // if we reached this point , there was not an error
                snapshot?.let {
                    val allArticles = ArrayList<Article>()
                    allArticles.add(Article(title = NEW_ARTICLE))
                    val documents = snapshot.documents
                    documents.forEach {
                        val article = it.toObject(Article::class.java)
                        article?.let {
                            allArticles.add(it)
                        }
                    }
                    articles.value = allArticles
                }
            }
        }

    }

    /**
     * @return articles from ArticleService
     * Adds/Posts Articles to the mutableLiveData<List<Article>>
     */
    fun fetchArticles() {
        viewModelScope.launch {
            var innerArticles = articleService.fetchArticles()
            articles.postValue(innerArticles);
        }
    }

    /**
     * Function is called in MainActivity from onCreate
     *  Saves article based on current Article that is pulled from DB
     */
    fun loadNewArticle(article: Article) {
        user?.let {
            val document =
                if (article.id == null || article.id.isEmpty()) {
                    //creating new article document for DB
                    firestore.collection("articles").document()
                } else {
                    //updating the existing article document for DB
                    firestore.collection("articles").document(article.id.toString())
                }

            article.id = document.id

            val handle = document.set(article)
            handle.addOnSuccessListener { Log.d("Firebase", "Document Saved") }
            handle.addOnFailureListener { Log.e("Firebase", "Load Failed $it") }
        }
    }

    /**
     * Function is called in MainActivity from signInResult function
     *  part of the signIn intent functionality
     */
    fun saveUser () {
        user?.let {
                user ->
            val handle = firestore.collection("users").document(user.uid).set(user)
            handle.addOnSuccessListener { Log.d("Firebase", "Document Saved") }
            handle.addOnFailureListener { Log.e("Firebase", "Save failed $it ") }
        }
    }
}
