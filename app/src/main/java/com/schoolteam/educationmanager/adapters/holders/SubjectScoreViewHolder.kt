package com.schoolteam.educationmanager.adapters.holders

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.schoolteam.educationmanager.adapters.ScoreAdapter
import com.schoolteam.educationmanager.models.SubjectScore
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycler_view_subject_score_item.*

class SubjectScoreViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!),
    LayoutContainer {

    @SuppressLint("SetTextI18n")
    fun display(subjectScore: SubjectScore, position: Int) {
        tvSubjectName.text = subjectScore.name
        tvSemester.text = subjectScore.semesterScore
        tvAverage.text = subjectScore.averageScore
        tvIndex.text = if (position == 0) "STT" else "$position"

        if (position == 0) {
            setVisible(true)
            tv5mins.text = "Miệng"
            tv15mins.text = "HS1"
            tv45mins.text = "HS2"
            setColor(Color.rgb(70, 193, 234), Color.WHITE)
            setHeaderTextViewStyle()
        } else {
            setVisible(false)
            setColor(Color.argb(12, 50, 19, 68), Color.BLACK)
        }

        setAdapter(rv5mins, subjectScore.list1)
        setAdapter(rv15mins, subjectScore.list2)
        setAdapter(rv45mins, subjectScore.list3)
    }

    private fun setHeaderTextViewStyle() {
        tv5mins.setBackgroundColor(Color.rgb(70, 193, 234))
        tv5mins.setTextColor(Color.WHITE)
        tv15mins.setBackgroundColor(Color.rgb(70, 193, 234))
        tv15mins.setTextColor(Color.WHITE)
        tv45mins.setBackgroundColor(Color.rgb(70, 193, 234))
        tv45mins.setTextColor(Color.WHITE)
    }

    private fun setAdapter(recyclerView: RecyclerView, list: List<Float>) {
        recyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ScoreAdapter(list)
    }

    private fun setColor(backgroundColor: Int, textColor: Int) {
        tvSubjectName.setBackgroundColor(backgroundColor)
        tvSubjectName.setTextColor(textColor)
        tvIndex.setBackgroundColor(backgroundColor)
        tvIndex.setTextColor(textColor)
        tvSemester.setBackgroundColor(backgroundColor)
        tvSemester.setTextColor(textColor)
        tvAverage.setBackgroundColor(backgroundColor)
        tvAverage.setTextColor(textColor)
    }

    private fun setVisible(isHeader: Boolean) {
        tv5mins.visibility = if (isHeader) View.VISIBLE else View.INVISIBLE
        tv15mins.visibility = if (isHeader) View.VISIBLE else View.INVISIBLE
        tv45mins.visibility = if (isHeader) View.VISIBLE else View.INVISIBLE
        rv5mins.visibility = if (isHeader) View.INVISIBLE else View.VISIBLE
        rv15mins.visibility = if (isHeader) View.INVISIBLE else View.VISIBLE
        rv45mins.visibility = if (isHeader) View.INVISIBLE else View.VISIBLE
    }
}