package com.schoolteam.educationmanager.controllers

import android.content.Context
import com.schoolteam.educationmanager.commons.Authorization
import com.schoolteam.educationmanager.commons.getToken
import com.schoolteam.educationmanager.services.ApiClient
import com.schoolteam.educationmanager.services.interfaces.ScoreService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object ScoreController {
    fun getScore(context: Context, userId: Int, semesterId: Int) = ApiClient
        .createService(ScoreService::class.java, hashMapOf(Authorization to context.getToken()))
        .getScore(userId, semesterId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}