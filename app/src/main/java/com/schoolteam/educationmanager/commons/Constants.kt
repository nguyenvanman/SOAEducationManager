package com.schoolteam.educationmanager.commons

const val PreferenceKeyUserId = "user_id"
const val PreferenceKeyUsername = "username"
const val PreferenceKeyUserFirstName = "first_name"
const val PreferenceKeyUserLastName = "last_name"
const val PreferenceKeyUserAvatarUrl = "avatar_url"
const val PreferenceKeyGroupId = "group_id"
const val PreferenceKeyGroupName = "group_name"
const val PreferenceKeyLoginToken = "login_token"
const val LoginState = "login_state"
const val RoleIntentKey = "role"
const val Authorization = "Authorization"

const val ApiBaseUrl = "https://educationmanagement20190316120819.azurewebsites.net/"

object Role {
    const val Student = "Student"
    const val Teacher = "Teacher"
    const val Parent = "Parent"
    const val Admin = "Admin"
    const val Mod = "Mod"
}

object MarkType {
    const val FiveMinutes = "Miệng"
    const val FifteenMinutes = "15 phút"
    const val OneLesson = "1 tiết"
    const val Semester = "Học kỳ"
    const val Average = "TBC"

}