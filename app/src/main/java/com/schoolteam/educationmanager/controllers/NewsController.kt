package com.schoolteam.educationmanager.controllers

import com.schoolteam.educationmanager.services.ApiClient
import com.schoolteam.educationmanager.services.interfaces.NewsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object NewsController {
    fun getNews() = ApiClient
        .createService(NewsService::class.java)
        .getNews()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}