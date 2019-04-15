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
import com.schoolteam.educationmanager.commons.doRequest
import com.schoolteam.educationmanager.controllers.NewsController
import kotlinx.android.synthetic.main.fragment_news.*
import org.jetbrains.anko.toast

class NewsFragment : Fragment() {
    private fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        coverView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pbLoading.visibility = View.GONE
        coverView.visibility = View.GONE
    }

    private lateinit var adapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context!!.doRequest({ NewsController.getNews() }, {
            showLoading()
        }, {
            adapter = NewsAdapter(context, it)
            rvNews.layoutManager = LinearLayoutManager(context)
            rvNews.adapter = adapter
            hideLoading()
        }, {
            context!!.toast(it)
            hideLoading()
        }, {
            context!!.toast(it as CharSequence)
            hideLoading()
        })
    }
}