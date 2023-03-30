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
    var articles : MutableLiveData<List<Article>> = MutableLiveData<List<Article>>(emptyList())
    var articleService : ArticleService = ArticleService()
    var user : User? = null

    private lateinit var firestore : FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    //WIP -AH
/*
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
    */
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
    fun saveUser(): Boolean {
        user?.let { user ->
            val handle = firestore.collection("users").document(user.uid).set(user)
            handle.addOnSuccessListener { Log.d("Firebase", "Document Saved") }
            handle.addOnFailureListener { Log.e("Firebase", "Save failed $it ") }
            return true
        }
        return false
    }
}
