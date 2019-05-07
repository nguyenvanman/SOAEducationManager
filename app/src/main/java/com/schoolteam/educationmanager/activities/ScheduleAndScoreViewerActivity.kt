package com.schoolteam.educationmanager.activities

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.ScheduleAdapter
import com.schoolteam.educationmanager.adapters.SubjectScoreAdapter
import com.schoolteam.educationmanager.commons.doRequest
import com.schoolteam.educationmanager.commons.showLoadingDialog
import com.schoolteam.educationmanager.controllers.SchoolYearController
import com.schoolteam.educationmanager.controllers.ScoreController
import com.schoolteam.educationmanager.controllers.StudentController
import com.schoolteam.educationmanager.models.dtos.responses.SchoolYear
import com.schoolteam.educationmanager.models.dtos.responses.Semester
import kotlinx.android.synthetic.main.activity_schedule_and_score_viewer.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ScheduleAndScoreViewerActivity : AppCompatActivity() {
    private var currentUserId = 0

    private var currentSemesterId = 0

    private var isViewingSchedule = false

    private lateinit var dialog: Dialog

    private lateinit var scheduleAdapter: ScheduleAdapter

    private lateinit var subjectScoreAdapter: SubjectScoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_and_score_viewer)
        init()
        getAndDisplaySchoolYears()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        dialog = showLoadingDialog().apply { dismiss() }
        currentUserId = intent!!.getIntExtra("userId", 0)
        isViewingSchedule = intent.hasExtra("flag")

        if (isViewingSchedule) {
            supportActionBar!!.title = getString(R.string.drawer_menu_scheduler)
            scheduleAdapter = ScheduleAdapter(this)
            recyclerView.layoutManager = GridLayoutManager(this, 13).also {
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
            recyclerView.adapter = scheduleAdapter
        } else {
            supportActionBar!!.title = getString(R.string.drawer_menu_score_board)
            subjectScoreAdapter = SubjectScoreAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = subjectScoreAdapter
        }
    }

    private fun showLoading() {
        dialog.show()
    }

    private fun hideLoading() {
        dialog.dismiss()
    }

    private fun displayNoContent() {
        recyclerView.visibility = View.GONE
        tvNoContent.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getAndDisplaySchoolYears() {
        doRequest({ SchoolYearController.getSchoolYears() },
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
            if (isViewingSchedule) {
                getAndDisplaySchedule()
            } else {
                getAndDisplayScore()
            }
        }

        val currentIndex = semesters.indexOfFirst { semester -> semester.isCurrent!! }
        semesters_spinner.selectedIndex = if (currentIndex != -1) currentIndex else 0
        currentSemesterId = (semesters_spinner.selectedItem as Semester).id!!
        if (isViewingSchedule) {
            getAndDisplaySchedule()
        } else {
            getAndDisplayScore()
        }
    }

    private fun getAndDisplaySchedule() {
        doRequest({ StudentController.getSchedule(this, currentUserId, currentSemesterId) },
            { showLoading() },
            {
                if (it.isEmpty()) {
                    displayNoContent()
                } else {
                    recyclerView.visibility = View.VISIBLE
                    tvNoContent.visibility = View.GONE
                    scheduleAdapter.list = it
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

    private fun getAndDisplayScore() {
        doRequest({ ScoreController.getScore(this, currentUserId, currentSemesterId) },
            { showLoading() },
            {
                hideLoading()
                if (!it.isEmpty()) {
                    recyclerView.visibility = View.VISIBLE
                    tvNoContent.visibility = View.GONE
                    subjectScoreAdapter.inputList = it
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

    override fun onBackPressed() {
        finish()
    }
}
