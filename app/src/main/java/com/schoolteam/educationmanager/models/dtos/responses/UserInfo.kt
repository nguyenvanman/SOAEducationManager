package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserInfo(
    @SerializedName("id") val id: Int?,
    @SerializedName("firstname") val firstName: String?,
    @SerializedName("lastname") val lastName: String?,
    @SerializedName("avatar") val avatarUrl: String?,
    @SerializedName("gender") val gender: Boolean?,
    @SerializedName("birthday") val dateOfBirth: Date?,
    @SerializedName("phone_number") val phoneNumber: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("identification_number") val identificationNumber: String?
)