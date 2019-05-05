package com.schoolteam.educationmanager.adapters.holders

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.schoolteam.educationmanager.models.SubjectScore
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycle_view_score_item.*

class SubjectScoreViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!),
    LayoutContainer {
    var subjectScore: SubjectScore? = null
        set(value) {
            field = value
            if (value != null) {
                display(value)
            }
        }

    var position: Int? = null
        set(value) {
            field = value
            setStyle(value)
        }

    private fun setStyle(pos: Int?) {
        setBackground(if (pos == 0) Color.rgb(70, 193, 234) else Color.argb(12, 50, 19, 68))
        setTextColor(if (pos == 0) Color.WHITE else Color.BLACK)
    }

    private fun setBackground(color: Int) {
        rootSubject.setBackgroundColor(color)
        root5mins.setBackgroundColor(color)
        root15mins.setBackgroundColor(color)
        root45mins.setBackgroundColor(color)
        rootSemester.setBackgroundColor(color)
        rootAverage.setBackgroundColor(color)
    }

    private fun setTextColor(color: Int) {
        tvSubject.setTextColor(color)
        tv5mins.setTextColor(color)
        tv15mins.setTextColor(color)
        tv45mins.setTextColor(color)
        tvSemester.setTextColor(color)
        tvAverage.setTextColor(color)
    }

    private fun display(subjectScore: SubjectScore) {
        tvSubject.text = subjectScore.name
        tv5mins.text = subjectScore.fiveMinutesScore
        tv15mins.text = subjectScore.fifteenMinutesScore
        tv45mins.text = subjectScore.oneLessonScore
        tvSemester.text = subjectScore.semesterScore
        tvAverage.text = subjectScore.averageScore
    }
}