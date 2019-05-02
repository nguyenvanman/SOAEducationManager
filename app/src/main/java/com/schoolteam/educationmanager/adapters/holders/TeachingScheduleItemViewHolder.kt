package com.schoolteam.educationmanager.adapters.holders

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.schoolteam.educationmanager.models.dtos.responses.TeachingScheduleItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycler_view_teaching_schedule_item.*

class TeachingScheduleItemViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!),
    LayoutContainer {
    var teachingScheduleItem: TeachingScheduleItem? = null
        set(value) {
            field = value
            display(value!!)
        }

    var position: Int? = null
        set(value) {
            field = value
            setStyle(value)
        }

    private fun setStyle(position: Int?) {
        if (position == null) return
        if (position < 7) {
            tvRoom.visibility = View.GONE
            tvClass.setTextColor(Color.WHITE)
            tvRoom.setTextColor(Color.WHITE)
            root.setBackgroundColor(Color.rgb(244, 97, 73))
        } else if (position == 7 || position == 43) {
            tvRoom.visibility = View.GONE
            tvClass.setTextColor(Color.WHITE)
            tvRoom.setTextColor(Color.WHITE)
            root.setBackgroundColor(Color.rgb(70, 193, 234))
        } else {
            tvRoom.visibility = View.VISIBLE
            tvClass.setTextColor(Color.BLACK)
            tvRoom.setTextColor(Color.BLACK)
            root.setBackgroundColor(Color.argb(12, 50, 19, 68))
        }
    }

    private fun display(teachingScheduleItem: TeachingScheduleItem) {
        tvClass.text = teachingScheduleItem.className
        tvRoom.text = teachingScheduleItem.room
    }
}