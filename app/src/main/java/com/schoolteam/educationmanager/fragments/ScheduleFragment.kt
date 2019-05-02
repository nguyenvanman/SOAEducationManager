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
import com.schoolteam.educationmanager.controllers.ParentController
import com.schoolteam.educationmanager.controllers.SchoolYearController
import com.schoolteam.educationmanager.controllers.StudentController
import com.schoolteam.educationmanager.models.dtos.responses.SchoolYear
import com.schoolteam.educationmanager.models.dtos.responses.Semester
import com.schoolteam.educationmanager.models.dtos.responses.Student
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : Fragment() {
    private lateinit var loadingDialog: Dialog

    var isStudentMode = true

    var currentUserId = 0

    var currentSemesterId = 0

    var currentYearId = 0

    private lateinit var adapter: ScheduleAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getStudents()
        if (isStudentMode) {
            currentUserId = context!!.getCurrentUserId()
            students_spinner.visibility = View.GONE
            getAndDisplaySchoolYears()
        } else {
            students_spinner.visibility = View.VISIBLE
            getStudents()
        }
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
                    currentYearId = (parent.getItemAtPosition(position) as SchoolYear).id!!
                    getAndDisplaySemester()
                }

                val currentIndex = it.indexOfFirst { schoolYear -> schoolYear.isCurrent!! }
                years_spinner.selectedIndex = if (currentIndex != -1) currentIndex else 0
                currentYearId = (years_spinner.selectedItem as SchoolYear).id!!
                getAndDisplaySemester()
            }, {
                displayNoContent()
                hideLoading()
            }, {
                displayNoContent()
                hideLoading()
            })
    }

    private fun getAndDisplaySemester() {
        context!!.doRequest({ SchoolYearController.getSemesters(currentYearId) },
            { showLoading() },
            {
                hideLoading()
                semesters_spinner.attachDataSource(it)
                semesters_spinner.addOnItemClickListener { parent, _, position, _ ->
                    currentSemesterId = (parent!!.getItemAtPosition(position) as Semester).id!!
                    getAndDisplaySchedule()
                }

                val currentIndex = it.indexOfFirst { semester -> semester.isCurrent!! }
                semesters_spinner.selectedIndex = if (currentIndex != -1) currentIndex else 0
                currentSemesterId = (semesters_spinner.selectedItem as Semester).id!!
                getAndDisplaySchedule()
            }, {
                displayNoContent()
                hideLoading()
            }, {
                displayNoContent()
                hideLoading()
            })
    }

    private fun getAndDisplaySchedule() {
        context!!.doRequest({ StudentController.getSchedule(context!!, currentUserId, currentSemesterId) },
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

    private fun displayNoContent() {
        rvSchedule.visibility = View.GONE
        tvNoContent.visibility = View.VISIBLE
    }

    private fun getStudents() {
        context!!.doRequest({ ParentController.getStudents(context!!, context!!.getCurrentUserId()) },
            { showLoading() },
            {
                hideLoading()
                if (!it.students!!.isEmpty()) {
                    students_spinner.attachDataSource(it.students)
                    students_spinner.addOnItemClickListener { parent, _, position, _ ->
                        currentUserId = (parent.getItemAtPosition(position) as Student).user!!.id!!
                        getAndDisplaySchedule()
                    }
                    currentUserId = (students_spinner.selectedItem as Student).user!!.id!!
                    getAndDisplaySchoolYears()
                } else {
                    displayNoContent()
                }
            }, {
                displayNoContent()
                hideLoading()
            }, {
                displayNoContent()
                hideLoading()
            })
    }
}
