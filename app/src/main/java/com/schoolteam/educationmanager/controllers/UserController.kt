package com.schoolteam.educationmanager.controllers

import android.content.Context
import com.schoolteam.educationmanager.commons.Authorization
import com.schoolteam.educationmanager.commons.getToken
import com.schoolteam.educationmanager.models.dtos.requests.UserInfo
import com.schoolteam.educationmanager.services.ApiClient
import com.schoolteam.educationmanager.services.interfaces.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object UserController {
    fun getUserInfo(context: Context, id: Int) = ApiClient
        .createService(UserService::class.java, hashMapOf(Authorization to context.getToken()))
        .getUserInfo(id)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())!!

    fun updateUserInfo(context: Context, id: Int, userInfo: UserInfo) = ApiClient
        .createService(UserService::class.java, hashMapOf(Authorization to context.getToken()))
        .updateUserInfo(id, userInfo)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())!!
}