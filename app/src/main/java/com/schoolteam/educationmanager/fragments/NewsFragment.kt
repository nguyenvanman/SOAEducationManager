package com.schoolteam.educationmanager.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.NewsAdapter
import com.schoolteam.educationmanager.commons.doRequest
import com.schoolteam.educationmanager.commons.showLoadingDialog
import com.schoolteam.educationmanager.controllers.NewsController
import com.schoolteam.educationmanager.models.dtos.responses.News
import kotlinx.android.synthetic.main.fragment_news.*
import org.jetbrains.anko.toast

class NewsFragment : Fragment() {
    private lateinit var loadingDialog: Dialog

    private fun showLoading() {
        loadingDialog.show()
    }

    private fun hideLoading() {
        loadingDialog.dismiss()
    }

    private lateinit var adapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = context!!.showLoadingDialog().apply { dismiss() }
        rvNews.layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter(context)
        rvNews.adapter = adapter

        queryNews()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            queryNews()
        }
    }

    private fun displayNews(list: List<News>) {
        adapter.list = list
    }

    private fun queryNews() {
        context!!.doRequest({ NewsController.getNews() }, {
            showLoading()
        }, {
            displayNews(it)
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