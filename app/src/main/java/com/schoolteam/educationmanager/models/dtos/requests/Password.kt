package com.schoolteam.educationmanager.models.dtos.requests

import com.google.gson.annotations.SerializedName

data class Password(
    @SerializedName("old_password") val oldPassword: String,
    @SerializedName("new_password") val newPassword: String
)