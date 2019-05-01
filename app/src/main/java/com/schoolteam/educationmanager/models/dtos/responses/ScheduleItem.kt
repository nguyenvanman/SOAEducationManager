package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class ScheduleItem(
    @SerializedName("id") val id: Int?,
    @SerializedName("subject_name") val subjectName: String?,
    @SerializedName("teacher_name") val teacherName: String?,
    @SerializedName("class_name") val className: String?,
    @SerializedName("day_of_week") val dayOfWeek: Int?,
    @SerializedName("lesson") val lesson: Int?
)