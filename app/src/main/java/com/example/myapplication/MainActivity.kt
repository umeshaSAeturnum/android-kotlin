package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.API.NewsAPI
import com.example.myapplication.Constants.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
   lateinit var newsAdapter: NewsAdapter
   lateinit var linearLayoutManager: LinearLayoutManager
   lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

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
                newsAdapter = NewsAdapter(baseContext, responseBody.articles)
                newsAdapter.notifyDataSetChanged()
                recyclerView.adapter = newsAdapter


            }

            override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })




    }
}