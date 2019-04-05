package com.schoolteam.educationmanager.services.interfaces

import com.schoolteam.educationmanager.models.dtos.requests.LoginBody
import com.schoolteam.educationmanager.models.dtos.responses.LoginResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("login")
    fun login(@Body loginBody : LoginBody) : Observable<Response<LoginResponse>>
}