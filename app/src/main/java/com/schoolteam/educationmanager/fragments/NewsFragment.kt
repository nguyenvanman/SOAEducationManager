package com.schoolteam.educationmanager.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.NewsAdapter
import com.schoolteam.educationmanager.controllers.NewsController
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NewsController.getNews().subscribe({
            if (it.code() == 200) {
                adapter = NewsAdapter(context, it.body()!!)
                rvNews.layoutManager = LinearLayoutManager(context)
                rvNews.adapter = adapter
            }
        }, {
            var x = it
        })
    }
}