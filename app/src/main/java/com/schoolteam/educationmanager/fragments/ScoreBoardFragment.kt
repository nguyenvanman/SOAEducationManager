package com.schoolteam.educationmanager.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.SubjectScoreAdapter
import com.schoolteam.educationmanager.commons.doRequest
import com.schoolteam.educationmanager.commons.getCurrentUserId
import com.schoolteam.educationmanager.commons.showLoadingDialog
import com.schoolteam.educationmanager.controllers.ParentController
import com.schoolteam.educationmanager.controllers.SchoolYearController
import com.schoolteam.educationmanager.controllers.ScoreController
import com.schoolteam.educationmanager.models.dtos.responses.SchoolYear
import com.schoolteam.educationmanager.models.dtos.responses.Semester
import com.schoolteam.educationmanager.models.dtos.responses.Student
import kotlinx.android.synthetic.main.fragment_score_board.*

class ScoreBoardFragment : Fragment() {
    private lateinit var loadingDialog: Dialog

    var isStudentMode = true

    var currentUserId = 0

    var currentSemesterId = 0

    private lateinit var adapter: SubjectScoreAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_score_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        if (isStudentMode) {
            currentUserId = context!!.getCurrentUserId()
            students_spinner.visibility = View.GONE
            getAndDisplaySchoolYears()
        } else {
            students_spinner.visibility = View.VISIBLE
            getStudents()
        }
    }

    private fun initViews() {
        loadingDialog = context!!.showLoadingDialog().apply { dismiss() }
        adapter = SubjectScoreAdapter()
        rvScoreBoard.layoutManager = LinearLayoutManager(context!!)
        rvScoreBoard.adapter = adapter
    }

    private fun hideLoading() {
        loadingDialog.dismiss()
    }

    private fun showLoading() {
        loadingDialog.show()
    }

    private fun displayNoContent() {
        rvScoreBoard.visibility = View.GONE
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
            getAndDisplayScore()
        }

        val currentIndex = semesters.indexOfFirst { semester -> semester.isCurrent!! }
        semesters_spinner.selectedIndex = if (currentIndex != -1) currentIndex else 0
        currentSemesterId = (semesters_spinner.selectedItem as Semester).id!!
        getAndDisplayScore()
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
                        getAndDisplayScore()
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

    private fun getAndDisplayScore() {
        context!!.doRequest({ ScoreController.getScore(context!!, currentUserId, currentSemesterId) },
            { showLoading() },
            {
                hideLoading()
                if (!it.isEmpty()) {
                    rvScoreBoard.visibility = View.VISIBLE
                    tvNoContent.visibility = View.GONE
                    adapter.inputList = it
                } else {
                    displayNoContent()
                }
            },
            {
                displayNoContent()
                hideLoading()
            },
            {
                displayNoContent()
                hideLoading()
            })
    }
}
