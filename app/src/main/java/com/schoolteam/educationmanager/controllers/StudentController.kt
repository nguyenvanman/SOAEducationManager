package com.schoolteam.educationmanager.controllers

import android.content.Context
import com.schoolteam.educationmanager.commons.Authorization
import com.schoolteam.educationmanager.commons.getToken
import com.schoolteam.educationmanager.services.ApiClient
import com.schoolteam.educationmanager.services.interfaces.StudentService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object StudentController {
    fun getSchedule(context: Context, userId: Int, semesterId: Int) = ApiClient
        .createService(StudentService::class.java, hashMapOf(Authorization to context.getToken()))
        .getSchedule(userId, semesterId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}