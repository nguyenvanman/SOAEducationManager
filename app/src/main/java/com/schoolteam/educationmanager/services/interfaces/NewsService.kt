package com.schoolteam.educationmanager.services.interfaces

import com.schoolteam.educationmanager.models.dtos.responses.News
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {
    @GET("api/news")
    fun getNews(): Observable<Response<List<News>>>
}