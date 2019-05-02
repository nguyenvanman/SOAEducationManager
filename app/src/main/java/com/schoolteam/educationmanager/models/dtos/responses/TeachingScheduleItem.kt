package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class TeachingScheduleItem(
    @SerializedName("id") val id: Int?,
    @SerializedName("class_name") val className: String?,
    @SerializedName("room") val room: String?,
    @SerializedName("day_of_week") val dayOfWeek: Int?,
    @SerializedName("lesson") val lesson: Int?
)