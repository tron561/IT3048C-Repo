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

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    // TODO: listenToArticles()
    //  listens to a Firestore collection called "articles" for changes and updates a LiveData object articles.value
    //  with the latest articles from the collection.

    fun fetchArticles() {
        viewModelScope.launch {
            var innerArticles = articleService.fetchArticles()
            articles.postValue(innerArticles);
        }
    }

    fun loadNewArticle(article: Article) {
        user?.let { user ->
            TODO("Not yet implemented")
            val document =
                if (article.id == null || article.id.isEmpty()) {
                    firestore.collection("articles").document()
                } else {
                    firestore.collection("articles").document(article.id.toString())
                }

            article.id = document.id

            val handle = document.set(article)
            handle.addOnSuccessListener { Log.d("Firebase", "Document Saved") }
            handle.addOnFailureListener { Log.e("Firebase", "Load Failed $it") }
        }
    }
    fun saveUser () {
        user?.let {
                user ->
            val handle = firestore.collection("users").document(user.uid).set(user)
            handle.addOnSuccessListener { Log.d("Firebase", "Document Saved") }
            handle.addOnFailureListener { Log.e("Firebase", "Save failed $it ") }
        }
    }
}
