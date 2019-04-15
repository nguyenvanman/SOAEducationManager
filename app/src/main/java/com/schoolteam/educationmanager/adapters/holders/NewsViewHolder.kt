package com.schoolteam.educationmanager.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.schoolteam.educationmanager.models.dtos.responses.News
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_recyclerview_news.*

class NewsViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {
    private lateinit var _news: News
    var news
        get() = _news
        set(value) {
            _news = value
            display(_news)
        }

    lateinit var click: (View, News) -> Unit

    init {
        itemView.setOnClickListener { click(itemView, _news) }
    }

    private fun display(news: News) {
        Glide.with(itemView).load(news.imageUrl).into(img_news)
        tvTitle.text = news.title
        tvSummary.text = news.summary
    }
}