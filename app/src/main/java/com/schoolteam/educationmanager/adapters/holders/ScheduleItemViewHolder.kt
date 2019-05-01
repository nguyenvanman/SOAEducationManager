package com.schoolteam.educationmanager.adapters.holders

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.schoolteam.educationmanager.models.dtos.responses.ScheduleItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycler_view_schedule_item_content.*

class ScheduleItemViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!),
    LayoutContainer {
    var scheduleItem: ScheduleItem? = null
        set(value) {
            field = value
            display(value!!)
        }

    var position: Int? = null
        set(value) {
            field = value
            setStyle(value)
        }

    @SuppressLint("ResourceAsColor")
    private fun setStyle(position: Int?) {
        if (position == null) return
        if (position < 7) {
            tvContent.setTextColor(Color.WHITE)
            tvContent.setBackgroundColor(Color.rgb(244, 97, 73))
        }
        if (position == 7 || position == 43) {
            tvContent.setTextColor(Color.WHITE)
            tvContent.setBackgroundColor(Color.rgb(70, 193, 234))
        }
    }

    private fun display(scheduleItem: ScheduleItem) {
        tvContent.text = scheduleItem.subjectName
    }
}