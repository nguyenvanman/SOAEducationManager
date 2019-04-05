package com.schoolteam.educationmanager.controllers

import com.schoolteam.educationmanager.models.dtos.requests.LoginBody
import com.schoolteam.educationmanager.services.ApiClient
import com.schoolteam.educationmanager.services.interfaces.AuthenticationService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object AuthenticationController {
    fun login(loginBody: LoginBody) = ApiClient
        .createService(AuthenticationService::class.java)
        .login(loginBody)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}