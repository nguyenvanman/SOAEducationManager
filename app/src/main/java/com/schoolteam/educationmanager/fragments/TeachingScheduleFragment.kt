package com.schoolteam.educationmanager.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.TeachingScheduleAdapter
import com.schoolteam.educationmanager.commons.doRequest
import com.schoolteam.educationmanager.commons.getCurrentUserId
import com.schoolteam.educationmanager.commons.showLoadingDialog
import com.schoolteam.educationmanager.controllers.SchoolYearController
import com.schoolteam.educationmanager.controllers.TeacherController
import com.schoolteam.educationmanager.models.dtos.responses.SchoolYear
import com.schoolteam.educationmanager.models.dtos.responses.Semester
import kotlinx.android.synthetic.main.fragment_schedule.*

class TeachingScheduleFragment : Fragment() {
    var currentUserId = 0

    var currentSemesterId = 0

    private lateinit var loadingDialog: Dialog

    private lateinit var adapter: TeachingScheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        currentUserId = context!!.getCurrentUserId()
        getAndDisplaySchoolYears()
    }

    private fun initView() {
        students_spinner.visibility = View.GONE
        loadingDialog = context!!.showLoadingDialog().apply { dismiss() }
        adapter = TeachingScheduleAdapter()
        rvSchedule.layoutManager = GridLayoutManager(context, 13).also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(p0: Int): Int {
                    if (p0 == 7 || p0 == 43) return 13
                    return if (p0 < 7) {
                        if (p0 == 0) 1 else 2
                    } else if (p0 <= 43) {
                        if (p0 % 7 == 1) 1 else 2
                    } else {
                        if (p0 % 7 == 2) 1 else 2
                    }
                }
            }
        }
        rvSchedule.adapter = adapter
    }

    private fun hideLoading() {
        loadingDialog.dismiss()
    }

    private fun showLoading() {
        loadingDialog.show()
    }

    private fun displayNoContent() {
        rvSchedule.visibility = View.GONE
        tvNoContent.visibility = View.VISIBLE
    }

    private fun getAndDisplaySchoolYears() {
        context!!.doRequest(
            { SchoolYearController.getSchoolYears(context!!) },
            { showLoading() },
            {
                hideLoading()
                years_spinner.attachDataSource(it)
                years_spinner.addOnItemClickListener { parent, _, position, _ ->
                    displaySemester((parent.getItemAtPosition(position) as SchoolYear).semesters!!)
                }

                val currentIndex = it.indexOfFirst { schoolYear -> schoolYear.isCurrent!! }
                years_spinner.selectedIndex = if (currentIndex != -1) currentIndex else 0
                displaySemester((years_spinner.selectedItem as SchoolYear).semesters!!)
            }, {
                displayNoContent()
                hideLoading()
            }, {
                displayNoContent()
                hideLoading()
            })
    }

    private fun displaySemester(semesters: List<Semester>) {
        semesters_spinner.attachDataSource(semesters)
        semesters_spinner.addOnItemClickListener { parent, _, position, _ ->
            currentSemesterId = (parent!!.getItemAtPosition(position) as Semester).id!!
            getAndDisplaySchedule()
        }

        val currentIndex = semesters.indexOfFirst { semester -> semester.isCurrent!! }
        semesters_spinner.selectedIndex = if (currentIndex != -1) currentIndex else 0
        currentSemesterId = (semesters_spinner.selectedItem as Semester).id!!
        getAndDisplaySchedule()
    }

    private fun getAndDisplaySchedule() {
        context!!.doRequest({ TeacherController.getSchedule(context!!, currentUserId, currentSemesterId) },
            { showLoading() },
            {
                if (it.isEmpty()) {
                    displayNoContent()
                } else {
                    rvSchedule.visibility = View.VISIBLE
                    tvNoContent.visibility = View.GONE
                    adapter.list = it
                }
                hideLoading()
            }, {
                displayNoContent()
                hideLoading()
            }, {
                displayNoContent()
                hideLoading()
            })
    }
}