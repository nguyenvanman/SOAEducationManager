package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("user") val user : User,
                         @SerializedName("group") val group: Group)