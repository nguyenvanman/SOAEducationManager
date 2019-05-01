package com.schoolteam.educationmanager.fragments


import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.ScheduleAdapter
import com.schoolteam.educationmanager.commons.doRequest
import com.schoolteam.educationmanager.commons.getCurrentUserId
import com.schoolteam.educationmanager.commons.showLoadingDialog
import com.schoolteam.educationmanager.controllers.SchoolYearController
import com.schoolteam.educationmanager.controllers.StudentController
import com.schoolteam.educationmanager.models.dtos.responses.SchoolYear
import com.schoolteam.educationmanager.models.dtos.responses.Semester
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : Fragment() {
    private lateinit var loadingDialog: Dialog

    private lateinit var adapter: ScheduleAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getAndDisplaySchoolYears()
    }

    private fun initView() {
        loadingDialog = context!!.showLoadingDialog().apply { dismiss() }
        adapter = ScheduleAdapter(context!!)
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

    private fun getAndDisplaySchoolYears() {
        context!!.doRequest({ SchoolYearController.getSchoolYears() },
            { showLoading() },
            {
                hideLoading()
                years_spinner.attachDataSource(it)
                years_spinner.addOnItemClickListener { parent, _, position, _ ->
                    getAndDisplaySemester((parent.getItemAtPosition(position) as SchoolYear).id!!)
                }

                val currentIndex = it.indexOfFirst { schoolYear -> schoolYear.isCurrent!! }
                years_spinner.selectedIndex = if (currentIndex != -1) currentIndex else 0
                getAndDisplaySemester((years_spinner.selectedItem as SchoolYear).id!!)
            },
            { hideLoading() },
            { hideLoading() })
    }

    private fun getAndDisplaySemester(yearId: Int) {
        context!!.doRequest({ SchoolYearController.getSemesters(yearId) },
            { showLoading() },
            {
                hideLoading()
                semesters_spinner.attachDataSource(it)
                semesters_spinner.addOnItemClickListener { parent, _, position, _ ->
                    getAndDisplaySchedule(
                        context!!.getCurrentUserId(),
                        (parent!!.getItemAtPosition(position) as Semester).id!!
                    )
                }

                val currentIndex = it.indexOfFirst { semester -> semester.isCurrent!! }
                semesters_spinner.selectedIndex = if (currentIndex != -1) currentIndex else 0
                getAndDisplaySchedule(context!!.getCurrentUserId(), (semesters_spinner.selectedItem as Semester).id!!)
            },
            { hideLoading() },
            { hideLoading() })
    }

    private fun getAndDisplaySchedule(userId: Int, semesterId: Int) {
        context!!.doRequest({ StudentController.getSchedule(context!!, userId, semesterId) },
            { showLoading() },
            {
                adapter.list = it
                hideLoading()
            },
            { hideLoading() },
            { hideLoading() })
    }
}
