package com.schoolteam.educationmanager.controllers

import android.content.Context
import com.schoolteam.educationmanager.commons.Authorization
import com.schoolteam.educationmanager.commons.getToken
import com.schoolteam.educationmanager.services.ApiClient
import com.schoolteam.educationmanager.services.interfaces.SchoolYearService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchoolYearController {
    fun getSchoolYears(context: Context) = ApiClient
        .createService(SchoolYearService::class.java, hashMapOf(Authorization to context.getToken()))
        .getSchoolYears()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

    fun getSemesters(schoolYearId: Int) = ApiClient
        .createService(SchoolYearService::class.java)
        .getSemesters(schoolYearId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}