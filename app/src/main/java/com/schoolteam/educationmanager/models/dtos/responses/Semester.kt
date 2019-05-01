package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class Semester(
    @SerializedName("id") val id: Int?,
    @SerializedName("start_time") val startTime: Date?,
    @SerializedName("end_time") val endTime: Date?,
    @SerializedName("name") val name: String?,
    @SerializedName("is_current") val isCurrent: Boolean?
) {
    override fun toString(): String = name!!
}