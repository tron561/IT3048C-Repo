package com.newsnow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
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
            try {
                var innerArticles = articleService.fetchArticles()
                articles.postValue(innerArticles)
            } catch (e: Exception) {
                Log.e("Firebase", "Fetch failed", e)
            }
        }
    }

    fun loadNewArticle(article: Article) {
        user?.let { user ->
            val document = createArticleDocument(article)
            saveDocument(document, article).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Document saved")
                } else {
                    Log.e("Firebase", "Load failed", task.exception)
                }
            }
        }
    }

    fun saveUser() {
        user?.let { user ->
            val document = firestore.collection("users").document(user.uid)
            saveUser(document, user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Document saved")
                } else {
                    Log.e("Firebase", "Save failed", task.exception)
                }
            }
        }
    }


    private fun createArticleDocument(article: Article): DocumentReference {
        val collection = firestore.collection("articles")
        val documentId = article.id
        return collection.document(documentId)
    }

    private fun saveDocument(document: DocumentReference, article: Article): Task<Void> {
        return document.set(article)
    }

    private fun saveUser(document: DocumentReference, user: User): Task<Void> {
        return document.set(user)
    }
}
