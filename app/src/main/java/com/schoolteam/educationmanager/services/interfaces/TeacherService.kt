package com.schoolteam.educationmanager.services.interfaces

import com.schoolteam.educationmanager.models.dtos.responses.TeachingScheduleItem
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TeacherService {
    @GET("api/teacher/{userId}/semester/{semesterId}/schedulers")
    fun getSchedule(@Path("userId") userId: Int, @Path("semesterId") semesterId: Int): Observable<Response<List<TeachingScheduleItem>>>
}