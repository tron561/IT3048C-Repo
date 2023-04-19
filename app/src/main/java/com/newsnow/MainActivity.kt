package com.newsnow
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsnow.databinding.ActivityMainBinding
import com.newsnow.fragments.InfoFragment
import com.newsnow.fragments.LoginFragment
import com.newsnow.fragments.MoreFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.currentsapi.services"

class MainActivity : AppCompatActivity() {

    lateinit var countdownTimer: CountDownTimer
    var seconds = 3L

    var titlesList = mutableListOf<String>()
    var descList = mutableListOf<String>()
    var imagesList = mutableListOf<String>()
    var linksList = mutableListOf<String>()
    lateinit var binding: ActivityMainBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvRecyclerView
        binding.vBlackScreen
        binding.progressBar
        binding.tvNoInternetCountDown
        binding.bottomNavigation
        makeAPIRequest()

        val infoFragment = InfoFragment()
        val loginFragment = LoginFragment()
        val moreFragment = MoreFragment()

        makeCurrentFragment(loginFragment)


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_info -> makeCurrentFragment(infoFragment)
                R.id.ic_login -> makeCurrentFragment(loginFragment)
                R.id.ic_more -> makeCurrentFragment(moreFragment)
            }
            true
        }

    }
    private fun makeCurrentFragment(fragment : Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }


    fun fadeIn() {
        binding.vBlackScreen.animate().apply {
            alpha(0f)
            duration = 3000
        }.start()
    }

    fun makeAPIRequest() {
        binding.progressBar.visibility = View.VISIBLE

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getNews()

                for (article in response.news) {
                    Log.d("MainActivity", "Result + $article")
                    addToUITile(article.title, article.description, article.image, article.url)
                }

                //updates ui when data has been retrieved
                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                    fadeIn()
                    binding.progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.d("MainActivity", e.toString())
                withContext(Dispatchers.Main) {
                    attemptRequestAgain(seconds)

                }
            }

        }
    }

    fun attemptRequestAgain(seconds: Long) {
        countdownTimer = object: CountDownTimer(seconds*1010,1000){
            override fun onFinish() {
                makeAPIRequest()
                countdownTimer.cancel()
                binding.tvNoInternetCountDown.visibility = View.GONE
                this@MainActivity.seconds+=3
            }
            override fun onTick(millisUntilFinished: Long) {
                binding.tvNoInternetCountDown.visibility = View.VISIBLE
                binding.tvNoInternetCountDown.text = "Cannot retrieve data...\nTrying again in: ${millisUntilFinished/1000}"
                Log.d("MainActivity", "Could not retrieve data. Trying again in ${millisUntilFinished/1000} seconds")
            }
        }
        countdownTimer.start()
    }

    private fun setUpRecyclerView() {
        binding.rvRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvRecyclerView.adapter = RecyclerAdapter(titlesList, descList, imagesList, linksList)
    }

    fun addToUITile(title: String, description: String, image: String, link: String) {
        linksList.add(link)
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }
}