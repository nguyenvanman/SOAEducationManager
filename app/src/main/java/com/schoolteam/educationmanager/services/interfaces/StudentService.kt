package com.schoolteam.educationmanager.services.interfaces

import com.schoolteam.educationmanager.models.dtos.responses.ScheduleItem
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentService {
    @GET("api/student/{userId}/semester/{semesterId}/schedulers")
    fun getSchedule(@Path("userId") userId: Int, @Path("semesterId") semesterId: Int): Observable<Response<List<ScheduleItem>>>
}