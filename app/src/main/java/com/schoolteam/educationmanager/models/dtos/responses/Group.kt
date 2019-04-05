package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class Group(@SerializedName("id") val id : Int?,
                 @SerializedName("name") val name : String?)