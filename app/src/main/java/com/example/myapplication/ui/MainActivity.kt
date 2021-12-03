package com.example.myapplication.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
//import com.example.myapplication.db.ArticleDatabase
import com.example.myapplication.models.Article
import com.example.myapplication.repository.NewsRepository
import com.example.myapplication.viewmodel.MainActivityViewModel
import com.example.myapplication.viewmodel.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_news.*

class MainActivity : AppCompatActivity(){
    private lateinit var navController: NavController
    lateinit var viewModel: MainActivityViewModel
    val fragment:NewsFragment = NewsFragment()
    val TAG_MAIN_ACTIVITY = "Main Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        navController = Navigation.findNavController(this,R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this,navController)

//        val newsRepository = NewsRepository(ArticleDatabase(this))
        val newsRepository = NewsRepository()

        //view model factory is used when there are parameters for viewModel constructor
        //if not ViewModelProviders.of() method internally creates default ViewModelProvider.Factory implementation for creating our ViewModel with no argument.
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(MainActivityViewModel::class.java)

        loadFragment(fragment)

    }

    fun loadFragment(newsFragment: NewsFragment){

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, newsFragment)
                .commitAllowingStateLoss()

    }

    fun getData(article: Article){
        Log.i(TAG_MAIN_ACTIVITY, "getting the bundle $article")
        val bundle = Bundle()
        bundle.putSerializable("article",article)
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

   }