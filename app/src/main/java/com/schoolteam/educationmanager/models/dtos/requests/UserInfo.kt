package com.schoolteam.educationmanager.models.dtos.requests

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("address") val address: String
)