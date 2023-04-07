package com.newsnow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.newsnow.service.ArticleService
import com.newsnow.dto.Article
import kotlinx.coroutines.launch
import com.newsnow.dto.User
import com.newsnow.service.ArticlesApi


class MainViewModel : ViewModel()  {

    internal val NEW_ARTICLE = "New Article"
    var articles : MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
    var articleService : ArticleService = ArticleService()
    var user : User? = null

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    private lateinit var firestore : FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        getNewsArticles()
    }

    private fun getNewsArticles() {
        viewModelScope.launch {
            try {
                val listResult = ArticlesApi.retrofitService.fetchArticles()
                if (listResult != null) {
                    _status.value = "Success: ${listResult.size} news articles retrieved"
                }
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

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
