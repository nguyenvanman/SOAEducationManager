package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class StudentsInfo(
    @SerializedName("parent_info") val parentInfo: ParentInfo?,
    @SerializedName("all_student") val students: List<Student>?
)