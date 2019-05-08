package com.schoolteam.educationmanager.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.holders.TeachingScheduleItemViewHolder
import com.schoolteam.educationmanager.models.dtos.responses.TeachingScheduleItem

class TeachingScheduleAdapter : RecyclerView.Adapter<TeachingScheduleItemViewHolder>() {
    var list: List<TeachingScheduleItem> = mutableListOf()
        set(value) {
            field = handlerData(value)
            notifyDataSetChanged()
        }

    private fun handlerData(inputList: List<TeachingScheduleItem>): List<TeachingScheduleItem> {
        val result = mutableListOf(
            TeachingScheduleItem(id = 1, className = "", room = "", dayOfWeek = 0, lesson = 0),
            TeachingScheduleItem(id = 1, className = "Thứ 2", room = "", dayOfWeek = 0, lesson = 0),
            TeachingScheduleItem(id = 1, className = "Thứ 3", room = "", dayOfWeek = 0, lesson = 0),
            TeachingScheduleItem(id = 1, className = "Thứ 4", room = "", dayOfWeek = 0, lesson = 0),
            TeachingScheduleItem(id = 1, className = "Thứ 5", room = "", dayOfWeek = 0, lesson = 0),
            TeachingScheduleItem(id = 1, className = "Thứ 6", room = "", dayOfWeek = 0, lesson = 0),
            TeachingScheduleItem(id = 1, className = "Thứ 7", room = "", dayOfWeek = 0, lesson = 0),
            TeachingScheduleItem(id = 1, className = "Sáng", room = "", dayOfWeek = 0, lesson = 0)
        )

        val list = inputList.sortedWith(compareBy({ it.lesson }, { it.dayOfWeek }))

        var currentDayOfWeek = 0
        var currentLesson = 0
        var curIndex = 8
        for (item in list) {
            if (currentDayOfWeek == item.dayOfWeek && currentLesson == item.lesson) {
                continue
            } else {
                currentDayOfWeek = item.dayOfWeek!!
                currentLesson = item.lesson!!
            }
            while (curIndex % 7 < item.dayOfWeek!! % 7 || curIndex / 7 < item.lesson!!) {
                if (curIndex == 43) {
                    result.add(
                        TeachingScheduleItem(
                            id = 1,
                            className = "Chiều",
                            room = "",
                            dayOfWeek = 0,
                            lesson = 0
                        )
                    )
                }
                if (curIndex % 7 == 1) {
                    result.add(
                        TeachingScheduleItem(
                            id = 1,
                            className = "${curIndex / 7}",
                            room = "",
                            dayOfWeek = 0,
                            lesson = 0
                        )
                    )
                } else {
                    result.add(
                        TeachingScheduleItem(
                            id = 1,
                            className = "",
                            room = "",
                            dayOfWeek = if (curIndex % 7 == 0) 7 else curIndex % 7,
                            lesson = curIndex / 7
                        )
                    )
                }
                curIndex++
            }

            result.add(item)
            curIndex++
        }

        while (result.size < 79) {
            if (curIndex == 43) {
                result.add(
                    TeachingScheduleItem(
                        id = 1,
                        className = "Chiều",
                        room = "",
                        dayOfWeek = 0,
                        lesson = 0
                    )
                )
            }
            if (curIndex % 7 == 1) {
                result.add(
                    TeachingScheduleItem(
                        id = 1,
                        className = "${curIndex / 7}",
                        room = "",
                        dayOfWeek = 0,
                        lesson = 0
                    )
                )
                curIndex++
            }
            result.add(
                TeachingScheduleItem(
                    id = 1,
                    className = "",
                    room = "",
                    dayOfWeek = if (curIndex % 7 == 0) 7 else curIndex % 7,
                    lesson = curIndex / 7
                )
            )
            curIndex++
        }

        return result
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TeachingScheduleItemViewHolder {
        return TeachingScheduleItemViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.recycler_view_teaching_schedule_item,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: TeachingScheduleItemViewHolder, p1: Int) {
        p0.apply {
            teachingScheduleItem = list[p1]
            position = p1
        }
    }
}