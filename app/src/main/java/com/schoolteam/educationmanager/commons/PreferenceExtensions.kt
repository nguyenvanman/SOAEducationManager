package com.schoolteam.educationmanager.commons

import android.content.Context
import com.schoolteam.educationmanager.models.dtos.responses.LoginResponse
import org.jetbrains.anko.defaultSharedPreferences

fun Context.saveLoginInformation(loginResponse: LoginResponse, loginToken: String) {
    defaultSharedPreferences.edit().apply {
        putInt(PreferenceKeyUserId, loginResponse.user.id!!)
        putString(PreferenceKeyUsername, loginResponse.user.username!!)
        putString(PreferenceKeyUserFirstName, loginResponse.user.firstName!!)
        putString(PreferenceKeyUserLastName, loginResponse.user.lastName!!)
        putString(PreferenceKeyUserAvatarUrl, loginResponse.user.avatarUrl!!)
        putInt(PreferenceKeyGroupId, loginResponse.group.id!!)
        putString(PreferenceKeyGroupName, loginResponse.group.name)
        putString(PreferenceKeyLoginToken, loginToken)
        putBoolean(LoginState, true)
        apply()
    }
}

fun Context.clearLoginInformation() {
    defaultSharedPreferences.edit().apply {
        remove(PreferenceKeyUserId)
        remove(PreferenceKeyUsername)
        remove(PreferenceKeyUserFirstName)
        remove(PreferenceKeyUserLastName)
        remove(PreferenceKeyUserAvatarUrl)
        remove(PreferenceKeyGroupId)
        remove(PreferenceKeyGroupName)
        remove(PreferenceKeyLoginToken)
        remove(LoginState)
        apply()
    }
}

fun Context.isLogin() = defaultSharedPreferences.getBoolean(LoginState, false)

fun Context.getGroup() = defaultSharedPreferences.getString(PreferenceKeyGroupName, "")!!

fun Context.getUserAvatarUrl() = defaultSharedPreferences.getString(PreferenceKeyUserAvatarUrl, "")

fun Context.getCurrentUserName() = "${defaultSharedPreferences.getString(PreferenceKeyUserFirstName, "")} " +
        defaultSharedPreferences.getString(PreferenceKeyUserLastName, "")
