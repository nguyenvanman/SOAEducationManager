package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class Class(
    @SerializedName("id") val id: Int?,
    @SerializedName("class_name") val className: String?
)