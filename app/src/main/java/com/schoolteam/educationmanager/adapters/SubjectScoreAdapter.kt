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
        val result = mutableListOf<SubjectScore>(
            SubjectScore("Môn học", "Miệng", "HS1", "HS2", "HK", "TB")
        )
        for (item in inputList) {
            var fiveMinutesScore = ""
            var fifteenMinutesScore = ""
            var oneLessonScore = ""
            var semesterScore = ""
            var averageScore = ""

            for (i in item.marks!!) {
                when (i.markType) {
                    MarkType.FiveMinutes -> fiveMinutesScore += "${i.mark}\t"
                    MarkType.FifteenMinutes -> fifteenMinutesScore += "${i.mark}\t"
                    MarkType.OneLesson -> oneLessonScore += "${i.mark}\t"
                    MarkType.Semester -> semesterScore += "${i.mark}\t"
                    MarkType.Average -> averageScore += "${i.mark}\t"
                }
            }
            result.add(
                SubjectScore(
                    item.subjectName,
                    fiveMinutesScore,
                    fifteenMinutesScore,
                    oneLessonScore,
                    semesterScore,
                    averageScore
                )
            )
        }
        return result
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SubjectScoreViewHolder {
        return SubjectScoreViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.recycle_view_score_item,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: SubjectScoreViewHolder, p1: Int) {
        p0.apply {
            subjectScore = list[p1]
            position = p1
        }
    }

}