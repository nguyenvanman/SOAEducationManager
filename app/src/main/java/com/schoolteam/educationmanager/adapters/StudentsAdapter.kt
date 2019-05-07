package com.schoolteam.educationmanager.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.activities.ScheduleAndScoreViewerActivity
import com.schoolteam.educationmanager.adapters.holders.StudentViewHolder
import com.schoolteam.educationmanager.models.dtos.responses.Student

class StudentsAdapter(val context: Context) : RecyclerView.Adapter<StudentViewHolder>() {
    var list: List<Student> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): StudentViewHolder {
        return StudentViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.rv_students_item, p0, false))
            .also {
                it.viewScore = this::viewScore
                it.viewSchedule = this::viewSchedule
            }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: StudentViewHolder, p1: Int) {
        p0.student = list[p1]
    }

    private fun viewSchedule(student: Student) {
        val intent = Intent(context, ScheduleAndScoreViewerActivity::class.java).apply {
            putExtra("userId", student.user!!.id)
            putExtra("flag", true)
        }
        context.startActivity(intent)
    }

    private fun viewScore(student: Student) {
        val intent = Intent(context, ScheduleAndScoreViewerActivity::class.java).apply {
            putExtra("userId", student.user!!.id)
        }
        context.startActivity(intent)
    }
}