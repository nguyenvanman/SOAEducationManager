package com.schoolteam.educationmanager.controllers

import android.content.Context
import com.schoolteam.educationmanager.commons.Authorization
import com.schoolteam.educationmanager.commons.getToken
import com.schoolteam.educationmanager.models.dtos.requests.LoginBody
import com.schoolteam.educationmanager.models.dtos.requests.Password
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

    fun changePassword(context: Context, userId: Int, password: Password) = ApiClient
        .createService(AuthenticationService::class.java, hashMapOf(Authorization to context.getToken()))
        .changePassword(userId, password)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}