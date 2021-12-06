package com.example.myapplication.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.db.ArticleDatabase
import com.example.myapplication.models.Article
import com.example.myapplication.repository.NewsRepository
import com.example.myapplication.viewmodel.MainActivityViewModel
import com.example.myapplication.viewmodel.NewsViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.news_item.view.*

class DetailActivity : AppCompatActivity() {
    lateinit var viewModel: MainActivityViewModel
    lateinit var article : Article
    val TAG_DETAIL = "Detail Activity"
    val APP_TITLE = "News APP"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val newsRepository = NewsRepository(ArticleDatabase(this))

        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(MainActivityViewModel::class.java)

        val actionbar = supportActionBar
        actionbar!!.title = APP_TITLE
        actionbar.setDisplayHomeAsUpEnabled(true)

        article = intent.getSerializableExtra("article") as Article

        article?.apply {
            Log.i(TAG_DETAIL,"getting details")
            Glide.with(this@DetailActivity)
                .load(urlToImage)
                .into(imageview_detail)
            author_detail.text = author
            news_title_detail.text = title
            description_detail.text = description
            content_detail.text = content
        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Log.e("aaa", "${article.author}")
            Snackbar.make(it, "Article saved successfully", Snackbar.LENGTH_LONG).show()
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}