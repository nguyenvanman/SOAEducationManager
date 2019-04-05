package com.schoolteam.educationmanager.commons

import android.app.Activity
import android.content.Context
import com.schoolteam.educationmanager.models.dtos.responses.LoginResponse

fun Activity.saveLoginInformation(loginResponse: LoginResponse, loginToken: String) {
    getPreferences(Context.MODE_PRIVATE).edit().apply {
        putInt(PreferenceKeyUserId, loginResponse.user.id!!)
        putString(PreferenceKeyUsername, loginResponse.user.username!!)
        putString(PreferenceKeyUserFirstName, loginResponse.user.firstName!!)
        putString(PreferenceKeyUserLastName, loginResponse.user.lastName!!)
        putString(PreferenceKeyUserAvatarUrl, loginResponse.user.avatarUrl!!)
        putInt(PreferenceKeyGroupId, loginResponse.group.id!!)
        putString(PreferenceKeyGroupName, loginResponse.group.name)
        putString(PreferenceKeyLoginToken, loginToken)
        apply()
    }
}