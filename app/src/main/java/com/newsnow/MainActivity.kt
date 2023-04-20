package com.newsnow
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsnow.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

const val BASE_URL = "https://api.currentsapi.services"

class MainActivity : AppCompatActivity() {

    lateinit var countdownTimer: CountDownTimer
    var seconds = 3L

    var titlesList = mutableListOf<String>()
    var descList = mutableListOf<String>()
    var imagesList = mutableListOf<String>()
    var linksList = mutableListOf<String>()
    lateinit var binding: ActivityMainBinding

    private val CHANNEL_ID = "channel_id_example_1"
    private val notificationID = 100

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvRecyclerView
        binding.vBlackScreen
        binding.progressBar
        binding.tvNoInternetCountDown
        makeAPIRequest()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Tile"
            val descriptionText = "Example Text"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            var description = descriptionText
        }
            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("RootKit Detected!")
            .setContentTitle("We've detected a RootKit on your system, thus this app is unable to be launched.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
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
        countdownTimer = object : CountDownTimer(seconds * 1010, 1000) {
            override fun onFinish() {
                makeAPIRequest()
                countdownTimer.cancel()
                binding.tvNoInternetCountDown.visibility = View.GONE
                this@MainActivity.seconds += 3
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.tvNoInternetCountDown.visibility = View.VISIBLE
                binding.tvNoInternetCountDown.text =
                    "Cannot retrieve data...\nTrying again in: ${millisUntilFinished / 1000}"
                Log.d(
                    "MainActivity",
                    "Could not retrieve data. Trying again in ${millisUntilFinished / 1000} seconds"
                )
            }
        }
        countdownTimer.start()
    }

    private fun setUpRecyclerView() {
        binding.rvRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvRecyclerView.adapter =
            RecyclerAdapter(titlesList, descList, imagesList, linksList)
    }

    fun addToUITile(title: String, description: String, image: String, link: String) {
        linksList.add(link)
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }

    private fun detectForSUBinaries(): Boolean {
        var suBinaries: Array<String> = arrayOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/system/su",
            "/system/bin/.ext/.su",
            "/system/usr/we-need-root/su-backup",
            "/system/xbin/mu"
        )

        for (bin in suBinaries) {
            if (File(bin).exists()) {
                Log.d("RootkitWarning", "Rootkit detected on host device. MainActivity aborted.")
                sendNotification()
                finish()

            }
        }
return false
    }
}