package com.schoolteam.educationmanager.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.holders.ScheduleItemViewHolder
import com.schoolteam.educationmanager.models.dtos.responses.ScheduleItem

class ScheduleAdapter(context: Context) : RecyclerView.Adapter<ScheduleItemViewHolder>() {
    var list: List<ScheduleItem> = mutableListOf()
        set(value) {
            field = handlerData(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ScheduleItemViewHolder {
        return ScheduleItemViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.recycler_view_schedule_item_content,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: ScheduleItemViewHolder, p1: Int) {
        p0.apply {
            scheduleItem = list[p1]
            position = p1
        }
    }

    private fun handlerData(list: List<ScheduleItem>): MutableList<ScheduleItem> {
        val result = mutableListOf(
            ScheduleItem(id = 1, subjectName = "", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 2", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 3", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 4", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 5", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 6", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 7", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Sáng", teacherName = "", className = "", dayOfWeek = 0, lesson = 0)
        )

        var curIndex = 8
        for (item in list) {
            if (curIndex % 7 == 1) {
                result.add(
                    ScheduleItem(
                        id = 1,
                        subjectName = "${curIndex / 7}",
                        teacherName = "",
                        className = "",
                        dayOfWeek = 0,
                        lesson = 0
                    )
                )
                curIndex++
            }

            if (curIndex == 43) {
                result.add(
                    ScheduleItem(
                        id = 1,
                        subjectName = "Chiều}",
                        teacherName = "",
                        className = "",
                        dayOfWeek = 0,
                        lesson = 0
                    )
                )
            }

            while (curIndex % 7 < item.dayOfWeek!! % 7 || curIndex / 7 < item.lesson!!) {
                result.add(
                    ScheduleItem(
                        id = 1,
                        subjectName = "",
                        teacherName = "",
                        className = "",
                        dayOfWeek = if (curIndex % 7 == 0) 7 else curIndex % 7,
                        lesson = curIndex / 7
                    )
                )
                curIndex++
            }

            result.add(item)
            curIndex++
        }

        while (result.size < 79) {
            if (curIndex == 43) {
                result.add(
                    ScheduleItem(
                        id = 1,
                        subjectName = "Chiều",
                        teacherName = "",
                        className = "",
                        dayOfWeek = 0,
                        lesson = 0
                    )
                )
            }
            if (curIndex % 7 == 1) {
                result.add(
                    ScheduleItem(
                        id = 1,
                        subjectName = "${curIndex / 7}",
                        teacherName = "",
                        className = "",
                        dayOfWeek = 0,
                        lesson = 0
                    )
                )
                curIndex++
            }
            result.add(
                ScheduleItem(
                    id = 1,
                    subjectName = "",
                    teacherName = "",
                    className = "",
                    dayOfWeek = if (curIndex % 7 == 0) 7 else curIndex % 7,
                    lesson = curIndex / 7
                )
            )
            curIndex++
        }

        return result
    }
}