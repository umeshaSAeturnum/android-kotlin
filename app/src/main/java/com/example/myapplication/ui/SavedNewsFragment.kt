package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Constants.Constants
import com.example.myapplication.Constants.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.myapplication.R
import com.example.myapplication.util.Resource
import com.example.myapplication.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SavedNewsFragment : Fragment() {

    lateinit var viewModel: MainActivityViewModel
    lateinit var newsAdapter: NewsAdapter
    var job: Job? = null

    val TAG_BREAK_NEWS = "NEWS_FRAGMENT_BREAKING_NEWS"
    val TAG_SEARCH_NEWS = "NEWS_FRAGMENT_SEARCH_NEWS"
    val TAG_LOADING = "NEWS_FRAGMENT"

    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setHasOptionsMenu(true)
        Log.i(TAG_LOADING,"fragment is loading")

        setupRecyclerView_breaking_news()
        setupRecyclerView_search_news()

        newsAdapter.setOnItemClickListener {

            (activity as MainActivity).getData(it)

        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
//                viewModel.deleteArticle(article)
                Snackbar.make(view, "SUccessfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("undo"){
//                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recycleViewSaved)
        }

//        viewModel.getSavednews().observe(viewLifecycleOwner, Observer { articles ->
//            newsAdapter.differ.submitList(articles)
//        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.app_bar_menu, menu)

        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                displaySearchResult(query,searchView)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                displaySearchResult(newText,searchView)
                return true
            }
        })
    }

    private fun setupRecyclerView_breaking_news(){
        newsAdapter  = NewsAdapter()
        recycleViewSaved.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setupRecyclerView_search_news(){
        newsAdapter  = NewsAdapter()
        recycleView.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun displaySearchResult(query : String?, searchView : SearchView){
        job?.cancel()
        job = MainScope().launch {
            delay(Constants.SEARCH_NEWS_TIME_DELAY)
            if (query != null) {
                viewModel.searchNews(query)
                searchView.clearFocus()
            }
        }
    }
}