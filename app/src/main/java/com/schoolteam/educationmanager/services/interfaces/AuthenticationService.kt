package com.schoolteam.educationmanager.services.interfaces

import com.schoolteam.educationmanager.models.dtos.requests.LoginBody
import com.schoolteam.educationmanager.models.dtos.requests.Password
import com.schoolteam.educationmanager.models.dtos.responses.LoginResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthenticationService {
    @POST("login")
    fun login(@Body loginBody : LoginBody) : Observable<Response<LoginResponse>>

    @PUT("api/account/{userId}/password")
    fun changePassword(@Path("userId") userId: Int, @Body password: Password): Observable<Response<String>>
}