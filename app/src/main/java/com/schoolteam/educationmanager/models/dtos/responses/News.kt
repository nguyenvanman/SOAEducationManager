package com.schoolteam.educationmanager.models.dtos.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class News(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("created_at") val createdAt: Date?
)