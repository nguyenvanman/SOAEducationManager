package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("id") val id : Int?,
                @SerializedName("username") val username : String?,
                @SerializedName("first_name") val firstName : String?,
                @SerializedName("last_name") val lastName : String?,
                @SerializedName("avatar_url") val avatarUrl: String?)