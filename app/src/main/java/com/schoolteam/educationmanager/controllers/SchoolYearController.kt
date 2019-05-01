package com.schoolteam.educationmanager.controllers

import com.schoolteam.educationmanager.services.ApiClient
import com.schoolteam.educationmanager.services.interfaces.SchoolYearService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchoolYearController {
    fun getSchoolYears() = ApiClient
        .createService(SchoolYearService::class.java)
        .getSchoolYears()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

    fun getSemesters(schoolYearId: Int) = ApiClient
        .createService(SchoolYearService::class.java)
        .getSemesters(schoolYearId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}