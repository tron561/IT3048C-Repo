package com.newsnow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.newsnow.dto.Article
import com.newsnow.service.ArticleService
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel() {

    internal val newArticle = "New Article"
    var articles: MutableLiveData<List<Article>> = MutableLiveData<List<Article>>(emptyList())
    var articleService: ArticleService = ArticleService()

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var error: MutableLiveData<Exception?> = MutableLiveData<Exception?>(null)

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val innerArticles = articleService.fetchArticles()
                articles.postValue(innerArticles)
                loading.postValue(false)
            } catch (e: Exception) {
                error.postValue(e)
                Log.e("MainViewModel", "Error fetching articles: $e")
                loading.postValue(false)
            }
        }
    }

    fun loadNewArticle(article: Article) {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val document = if (article.id == null || article.id.isEmpty()) {
                    firestore.collection("articles").document()
                } else {
                    firestore.collection("articles").document(article.id.toString())
                }

                article.id = document.id

                document.set(article).await()

                Log.d("Firebase", "Document Saved")
                loading.postValue(false)
            } catch (e: Exception) {
                error.postValue(e)
                Log.e("MainViewModel", "Error loading new article: $e")
                loading.postValue(false)
            }
        }
    }

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
*/