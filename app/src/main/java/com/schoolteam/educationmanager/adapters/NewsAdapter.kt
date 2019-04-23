package com.schoolteam.educationmanager.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.activities.NewsDetailActivity
import com.schoolteam.educationmanager.adapters.holders.NewsViewHolder
import com.schoolteam.educationmanager.models.dtos.responses.News

class NewsAdapter(val context: Context?) : RecyclerView.Adapter<NewsViewHolder>() {
    var isClickable = true

    private var _list = listOf<News>()

    var list: List<News>
        get() = _list
        set(value) {
            _list = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.item_recyclerview_news, p0, false)
        return NewsViewHolder(itemView).also {
            it.click = { _, news ->
                if (isClickable) {
                    val intent = Intent(context, NewsDetailActivity::class.java).apply {
                        putExtra("image_url", news.imageUrl)
                        putExtra("title", news.title)
                        putExtra("summary", news.summary)
                        putExtra("content", news.content)
                    }
                    context!!.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount() = _list.size

    override fun onBindViewHolder(p0: NewsViewHolder, p1: Int) {
        p0.news = _list[p1]
    }
}