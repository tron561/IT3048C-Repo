package com.newsnow

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.newsnow.databinding.ActivityMainBinding
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/*import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList*/

@RunWith(AndroidJUnit4::class)
class ArticleUnitTests {
   /* @get:Rule var rule: TestRule = InstantTaskExecutorRule()

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
        articles.add(Article("50 ways to recycle", "https://www.nytimes.com", "Jane Doe", "Here are 50 different ways you can practice sustainability.", "2023-02-01", "A1B2C3D4"))
        articles.add(Article("Presidential Election", "https://www.nytimes.com", "Jane Doe", "Who will win? Will Joe Biden win a second term?", "2023-02-02", "A1B2C3D4"))
        articles.add(Article("Rihanna is BACK", "https://www.nytimes.com", "Jane Doe", "International superstars performs at the Super Bowl Halftime Show.", "2023-02-03", "A1B2C3D4"))

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
            fun onChanged(receivedArticles: List<Article>?) {
                allArticles = receivedArticles
                latch.countDown()
                mvm.articles.removeObserver(this)
            }

            override fun onChanged(value: List<Article>) {
                TODO("Not yet implemented")
            }
        }
        mvm.articles.observeForever(observer)
        latch.await(10, TimeUnit.SECONDS)
        assertNotNull(allArticles)
        assertTrue(allArticles!!.isNotEmpty())
        var containsRihanna = false
        allArticles!!.forEach {
            if (it.title.equals(("Rihanna is BACK")) && it.fullDescription.equals("International superstars performs at the Super Bowl Halftime Show.")) {
                containsRihanna = true
            }
        }
        assertTrue(containsRihanna)
    }*/

    // list of tests to make

    /* onCreate, should inflate layoutInflater, setContentView,
    check bindings, and call API Request test */

    // fadeIn
    @Test
    fun testFadeIn() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            val binding = ActivityMainBinding.bind(activity.findViewById(android.R.id.content))
            val fadeInDuration = 3000L

            // Create a CountDownLatch with a count of 1 to block the test thread
            val latch = CountDownLatch(1)

            // Call the fadeIn method on the UI thread
            activity.runOnUiThread {
                binding.vBlackScreen.alpha = 0f
                binding.vBlackScreen.animate().apply {
                    alpha(1f)
                    duration = fadeInDuration
                    withEndAction { latch.countDown() }
                }.start()
            }

            // Wait for the animation to finish or timeout after 5 seconds
            assertTrue(latch.await(fadeInDuration + 2000L, TimeUnit.MILLISECONDS))

            // Check that the black screen has alpha value of 1.0
            assertEquals(1f, binding.vBlackScreen.alpha, 0.1f)
        }
        scenario.close()
    }
}


    // makeAPIRequest, will be called in top level unit test
    // attempting request again, looking for error text
    // recycler view?
    // UI reads the list
