package com.schoolteam.educationmanager.adapters.holders

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.schoolteam.educationmanager.models.dtos.responses.Student
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rv_students_item.*

class StudentViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {
    var student: Student? = null
        set(value) {
            field = value
            if (value != null) {
                display(value)
            }
        }

    lateinit var viewSchedule: (Student) -> Unit

    lateinit var viewScore: (Student) -> Unit

    @SuppressLint("SetTextI18n")
    private fun display(student: Student) {
        Glide.with(itemView.context).load(student.user!!.avatarUrl).into(imgAvatar)
        tvFullName.text = "${student.user.lastName} ${student.user.firstName}"
        tvClass.text = "Lớp ${student.className!!.className}"
        tvGender.text = if (student.user.gender!!) "Giới tính: Nam" else "Giới tính: Nữ"
        tvAddress.text = "Địa chỉ: ${student.user.address}"
        tvPhoneNumber.text = "SĐT: ${student.user.phoneNumber}"
    }

    init {
        btnViewSchedule.setOnClickListener { viewSchedule.invoke(student!!) }
        btnViewScore.setOnClickListener { viewScore.invoke(student!!) }
    }
}