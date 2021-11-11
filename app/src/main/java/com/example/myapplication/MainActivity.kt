package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.TextView
import com.example.myapplication.API.NewsAPI
import com.example.myapplication.Constants.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
   lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
//        textView = findViewById(R.id.txtId)

        getnewsData()
    }

    private fun getnewsData() {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NewsAPI::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<NewsResponse?> {
            override fun onResponse(call: Call<NewsResponse?>, response: Response<NewsResponse?>) {
               val responseBody = response.body()!!
                val stringBuilder = StringBuilder()
                for (data in responseBody.articles){
                    stringBuilder.append(data.title)
                }

//                textView.text = stringBuilder
            }

            override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })




    }
}