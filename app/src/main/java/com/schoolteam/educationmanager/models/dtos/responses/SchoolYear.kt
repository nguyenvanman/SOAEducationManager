package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName

data class SchoolYear(
    @SerializedName("id") val id: Int?,
    @SerializedName("start_year") val startYear: Int?,
    @SerializedName("end_year") val endYear: Int?,
    @SerializedName("is_current") val isCurrent: Boolean?,
    @SerializedName("semester_info") val semesters: List<Semester>?
) {
    override fun toString(): String {
        return "Năm học $startYear - $endYear"
    }
}