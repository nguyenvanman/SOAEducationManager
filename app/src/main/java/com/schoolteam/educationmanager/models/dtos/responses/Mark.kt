package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class Mark(
    @SerializedName("id") val id: Int?,
    @SerializedName("mark") val mark: Float?,
    @SerializedName("type_mark") val markType: String?
)