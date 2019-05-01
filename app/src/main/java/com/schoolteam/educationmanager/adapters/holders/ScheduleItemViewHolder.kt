package com.schoolteam.educationmanager.adapters.holders

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

    private fun display(scheduleItem: ScheduleItem) {
        tvContent.text = scheduleItem.subjectName
    }
}