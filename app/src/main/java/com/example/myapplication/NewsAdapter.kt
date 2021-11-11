package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter (val context: Context, val newsList: List<Article>) :RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(news_item: View): RecyclerView.ViewHolder(news_item) {
        var title: TextView
        var author: TextView
        var description: TextView
        var image: ImageView

        init {
            title = news_item.findViewById(R.id.news_title)
            author = news_item.findViewById(R.id.author)
            description = news_item.findViewById(R.id.description)
            image = news_item.findViewById(R.id.imageview)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.author.text = newsList[position].author
        holder.description.text = newsList[position].description
        holder.title.text = newsList[position].title
        Glide.with(context)
            .load(newsList[position].urlToImage)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

}


