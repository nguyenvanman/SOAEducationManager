package com.schoolteam.educationmanager.services.interfaces

import com.schoolteam.educationmanager.models.dtos.responses.SchoolYear
import com.schoolteam.educationmanager.models.dtos.responses.Semester
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SchoolYearService {
    @GET("api/schoolyear")
    fun getSchoolYears(): Observable<Response<List<SchoolYear>>>

    @GET("api/year/{schoolYearId}/semesters")
    fun getSemesters(@Path("schoolYearId") schoolYearId: Int): Observable<Response<List<Semester>>>
}