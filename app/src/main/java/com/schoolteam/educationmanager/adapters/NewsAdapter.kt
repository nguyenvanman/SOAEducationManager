package com.schoolteam.educationmanager.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.holders.NewsViewHolder
import com.schoolteam.educationmanager.models.dtos.responses.News

class NewsAdapter(context: Context?, private val list: List<News>) : RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.item_recyclerview_news, p0, false)
        return NewsViewHolder(itemView).also {
            it.click = { _, _ ->
                var x = 1
            }
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(p0: NewsViewHolder, p1: Int) {
        p0.news = list[p1]
    }
}