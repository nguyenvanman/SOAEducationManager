package com.schoolteam.educationmanager.services.interfaces

import com.schoolteam.educationmanager.models.dtos.responses.SubjectScore
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ScoreService {
    @GET("api/student/{userId}/semester/{semesterId}/marks")
    fun getScore(@Path("userId") userId: Int, @Path("semesterId") semesterId: Int): Observable<Response<List<SubjectScore>>>
}