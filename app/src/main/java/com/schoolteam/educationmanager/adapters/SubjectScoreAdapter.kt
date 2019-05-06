package com.schoolteam.educationmanager.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.holders.SubjectScoreViewHolder
import com.schoolteam.educationmanager.commons.MarkType
import com.schoolteam.educationmanager.models.SubjectScore

class SubjectScoreAdapter : RecyclerView.Adapter<SubjectScoreViewHolder>() {
    private var list: List<SubjectScore> = mutableListOf()

    var inputList: List<com.schoolteam.educationmanager.models.dtos.responses.SubjectScore> = mutableListOf()
        set(value) {
            field = value
            list = handlerData(inputList)
            notifyDataSetChanged()
        }

    private fun handlerData(inputList: List<com.schoolteam.educationmanager.models.dtos.responses.SubjectScore>): List<SubjectScore> {
        val result = mutableListOf(SubjectScore("Môn học", "HK", "TB"))
        for (item in inputList) {
            var semesterScore = ""
            var averageScore = ""
            val list1 = mutableListOf<Float>()
            val list2 = mutableListOf<Float>()
            val list3 = mutableListOf<Float>()
            for (i in item.marks!!) {
                when (i.markType) {
                    MarkType.FiveMinutes -> {
                        list1.add(i.mark!!)
                    }
                    MarkType.FifteenMinutes -> {
                        list2.add(i.mark!!)
                    }
                    MarkType.OneLesson -> {
                        list3.add(i.mark!!)
                    }
                }
            }
            if (item.marks.any { it.markType == MarkType.Semester }) {
                semesterScore = "${item.marks.first { it.markType == MarkType.Semester }.mark}"
            }
            if (item.marks.any { it.markType == MarkType.Average }) {
                averageScore = "${item.marks.first { it.markType == MarkType.Average }.mark}"
            }
            result.add(
                SubjectScore(
                    item.subjectName,
                    semesterScore,
                    averageScore,
                    list1,
                    list2,
                    list3
                )
            )
        }

        var max1 = 2
        var max2 = 2
        var max3 = 2

        result.forEach {
            if (it.list1.size > max1) {
                max1 = it.list1.size
            }
            if (it.list2.size > max2) {
                max2 = it.list2.size
            }
            if (it.list3.size > max3) {
                max3 = it.list3.size
            }
        }

        result.forEach {
            while (it.list1.size < max1) {
                it.list1.add(-1f)
            }
            while (it.list2.size < max2) {
                it.list2.add(-1f)
            }
            while (it.list3.size < max3) {
                it.list3.add(-1f)
            }
        }
        return result
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SubjectScoreViewHolder {
        return SubjectScoreViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.recycler_view_subject_score_item,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: SubjectScoreViewHolder, p1: Int) {
        p0.apply {
            p0.display(list[p1], p1)
        }
    }
}