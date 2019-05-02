package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class ParentInfo(
    @SerializedName("id") val id: Int?,
    @SerializedName("parent_name") val parentName: String?
)