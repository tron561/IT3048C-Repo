package com.newsnow

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.newsnow.api.ArticleItem
import com.newsnow.api.JSONData
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class NewsNowUnitTests {



    @Mock
    private lateinit var api: APIRequest

    @Test
    suspend fun testMakeApiRequest() {
        var activity = MainActivity()

        //Given
        val response = JSONData(
            listOf(ArticleItem(
                    "test author", listOf("test category"), "test description", "123455",
                    "test image", "english", "1-1-2023", "test title", "test url"


            )), 1, "success"
        )
        Mockito.`when`(api.getNews()).thenReturn(response)

        //When
        MainActivity().makeAPIRequest()

        //Then
        //TODO: assert that UI is updated with the data retrieved from API
        val layoutManager = activity.binding.rvRecyclerView.layoutManager
        val adapter = activity.binding.rvRecyclerView.adapter
        assert(layoutManager is LinearLayoutManager)
        assert(adapter is RecyclerAdapter)
        assert(adapter!!.itemCount == 1)
    }

    @Test
    fun testAttemptRequestAgain() {
        //Given
        val activity = MainActivity()
        val countdownTimer = activity.countdownTimer
        activity.seconds = 3L

        //When
        activity.attemptRequestAgain(3L)

        //Then
        assert(activity.countdownTimer != countdownTimer)
        //TODO: assert that the countdown timer starts and updates the UI
    }

    @Test
    fun testAddToUITile() {
        //Given
        val activity = MainActivity()
        val title = "Test Title"
        val description = "Test Description"
        val image = "Test Image"
        val link = "Test Link"

        //When
        activity.addToUITile(title, description, image, link)

        //Then
        assert(activity.titlesList.contains(title))
        assert(activity.descList.contains(description))
        assert(activity.imagesList.contains(image))
        assert(activity.linksList.contains(link))
    }
}
