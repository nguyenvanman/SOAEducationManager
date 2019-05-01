package com.schoolteam.educationmanager.fragments


import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
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
import com.schoolteam.educationmanager.models.dtos.responses.ScheduleItem
import com.schoolteam.educationmanager.models.dtos.responses.SchoolYear
import com.schoolteam.educationmanager.models.dtos.responses.Semester
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : Fragment() {
    private lateinit var loadingDialog: Dialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = context!!.showLoadingDialog().apply { dismiss() }
        getAndDisplaySchoolYears()
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
                hideLoading()

                rvSchedule.layoutManager = LinearLayoutManager(context!!)
                val adapter = ScheduleAdapter(context!!)
                rvSchedule.adapter = adapter

                adapter.list = test(it)
                var y = 1
                displaySchedule(it)
            },
            { hideLoading() },
            { hideLoading() })
    }

    private fun displaySchedule(schedule: List<ScheduleItem>) {
        var x = 1
    }

    private fun test(list: List<ScheduleItem>): MutableList<ScheduleItem> {
        val result = mutableListOf(
            ScheduleItem(id = 1, subjectName = "Sáng", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "0", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 2", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 3", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 4", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 5", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 6", teacherName = "", className = "", dayOfWeek = 0, lesson = 0),
            ScheduleItem(id = 1, subjectName = "Thứ 7", teacherName = "", className = "", dayOfWeek = 0, lesson = 0)
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
                        subjectName = "Chiều}",
                        teacherName = "",
                        className = "",
                        dayOfWeek = 0,
                        lesson = 0
                    )
                )
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
