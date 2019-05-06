package com.schoolteam.educationmanager.models

data class SubjectScore(
    val name: String?,
    val semesterScore: String?,
    val averageScore: String?,
    var list1: MutableList<Float> = mutableListOf(),
    var list2: MutableList<Float> = mutableListOf(),
    var list3: MutableList<Float> = mutableListOf()
)