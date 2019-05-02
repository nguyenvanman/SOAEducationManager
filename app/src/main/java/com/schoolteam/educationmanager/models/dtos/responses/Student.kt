package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("id") val id: Int?,
    @SerializedName("user") val user: UserInfo?,
    @SerializedName("class_name") val className: Class?
) {
    override fun toString(): String {
        return "${user!!.firstName} ${user.lastName} - Lớp ${className!!.className}"
    }
}