package app.newsnow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.newsnow.service.ArticleService
import app.src.main.java.app.newsnow.dto.Article

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