package app.newsnow.service

import app.newsnow.RetrofitClientInstance
import app.newsnow.dao.IArticleDAO
import app.src.main.java.app.newsnow.dto.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class ArticleService {
    suspend fun fetchArticles() : List<Article>? {
        return withContext(Dispatchers.IO){
            val service = RetrofitClientInstance.retrofitInstance?.create(IArticleDAO::class.java)
            val articles = async {service?.getAllArticles()}
            var result = articles.await()?.awaitResponse()?.body()
//            updateLocalPlants(result)
            return@withContext result
        }
    }
}