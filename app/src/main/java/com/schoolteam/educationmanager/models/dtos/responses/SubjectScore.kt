package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class SubjectScore(
    @SerializedName("subject_name") val subjectName: String?,
    @SerializedName("all_mark") val marks: List<Mark>?
)