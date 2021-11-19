package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.repository.NewsRepository
import com.example.myapplication.viewmodel.MainActivityViewModel
import com.example.myapplication.viewmodel.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainActivityViewModel
    val fragment:NewsFragment = NewsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(MainActivityViewModel::class.java)

        loadFragment(fragment)



    }

    fun loadFragment(newsFragment: NewsFragment){

        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, newsFragment)
                .commitAllowingStateLoss()

    }
}