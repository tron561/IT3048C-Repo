package com.newsnow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsnow.service.ArticleService
import com.newsnow.dto.Article
import kotlinx.coroutines.launch

class MainViewModel : ViewModel()  {
    var articles : MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
    var articleService : ArticleService = ArticleService()

    fun fetchArticles() {
        viewModelScope.launch {
            var innerArticles = articleService.fetchArticles()
            articles.postValue(innerArticles);
        }
    }
}