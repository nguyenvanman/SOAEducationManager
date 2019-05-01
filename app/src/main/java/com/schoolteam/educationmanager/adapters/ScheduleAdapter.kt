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
            field = value
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
        p0.scheduleItem = list[p1]
    }

}