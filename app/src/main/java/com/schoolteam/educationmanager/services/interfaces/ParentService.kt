package com.schoolteam.educationmanager.services.interfaces

import com.schoolteam.educationmanager.models.dtos.responses.StudentsInfo
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ParentService {
    @GET("api/parent/{parentId}/students")
    fun getStudents(@Path("parentId") parentId: Int): Observable<Response<StudentsInfo>>
}