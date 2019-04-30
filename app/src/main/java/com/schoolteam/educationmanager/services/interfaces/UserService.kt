package com.schoolteam.educationmanager.services.interfaces

import com.schoolteam.educationmanager.models.dtos.responses.UserInfo
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("api/user/{id}")
    fun getUserInfo(@Path("id") id: Int): Observable<Response<UserInfo>>

    @PUT("api/user/{id}")
    fun updateUserInfo(@Path("id") id: Int, @Body userInfo: com.schoolteam.educationmanager.models.dtos.requests.UserInfo): Observable<Response<UserInfo>>
}