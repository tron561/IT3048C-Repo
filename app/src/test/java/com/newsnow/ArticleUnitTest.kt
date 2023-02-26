package com.newsnow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.newsnow.service.ArticleService
import com.newsnow.dto.Article
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class ArticleUnitTests {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    lateinit var mvm : MainViewModel

    @MockK
    lateinit var mockArticleService : ArticleService

    private val mainThreadSurrogate = newSingleThreadContext("Main Thread")

    @Before
    fun initMocksAndMainThread() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `given a view model with live data when populated with articles then results show rihanna` () {
        givenViewModelIsInitializedWithMockData()
        whenArticleServiceFetchArticlesInvoked()
        thenResultsShouldContainRihanna()
    }

    private fun givenViewModelIsInitializedWithMockData() {
        val articles = ArrayList<Article>()
        // creating mock list of article items
        articles.add(Article("50 ways to recycle", "https://www.nytimes.com", "Jane Doe", "Here are 50 different ways you can practice sustainability.", "2023-02-01", 1))
        articles.add(Article("Presidential Election", "https://www.nytimes.com", "Jane Doe", "Who will win? Will Joe Biden win a second term?", "2023-02-02", 2))
        articles.add(Article("Rihanna is BACK", "https://www.nytimes.com", "Jane Doe", "International superstars performs at the Super Bowl Halftime Show.", "2023-02-03", 3))

        // initialize mvm
        mvm = MainViewModel()

        coEvery { mockArticleService.fetchArticles() } returns articles

        mvm.articleService = mockArticleService
    }

    private fun whenArticleServiceFetchArticlesInvoked() {
        mvm.fetchArticles()
    }

    // searching for article type that contains rihanna, which is the last article item in the mocked articles
    private fun thenResultsShouldContainRihanna() {
        var allArticles : List<Article>? = ArrayList<Article>()
        val latch = CountDownLatch(1)
        val observer = object : Observer<List<Article>> {
            override fun onChanged(receivedArticles: List<Article>?) {
                allArticles = receivedArticles
                latch.countDown()
                mvm.articles.removeObserver(this)
            }
        }
        mvm.articles.observeForever(observer)
        latch.await(10, TimeUnit.SECONDS)
        assertNotNull(allArticles)
        assertTrue(allArticles!!.isNotEmpty())
        var containsRihanna = false
        allArticles!!.forEach {
            if (it.title.equals(("Rihanna is BACK")) && it.full_description.equals("International superstars performs at the Super Bowl Halftime Show.")) {
                containsRihanna = true
            }
        }
        assertTrue(containsRihanna)
    }
}