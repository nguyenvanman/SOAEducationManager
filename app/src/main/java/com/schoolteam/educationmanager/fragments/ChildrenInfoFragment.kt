package com.schoolteam.educationmanager.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.StudentsAdapter
import com.schoolteam.educationmanager.commons.doRequest
import com.schoolteam.educationmanager.commons.getCurrentUserId
import com.schoolteam.educationmanager.commons.showLoadingDialog
import com.schoolteam.educationmanager.controllers.ParentController
import kotlinx.android.synthetic.main.fragment_children_info.*

class ChildrenInfoFragment : Fragment() {
    private lateinit var adapter: StudentsAdapter

    private lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_children_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getStudents()
    }

    private fun initViews() {
        adapter = StudentsAdapter(context!!)
        rvStudents.layoutManager = LinearLayoutManager(context)
        rvStudents.adapter = adapter
        dialog = context!!.showLoadingDialog().apply { dismiss() }
    }

    private fun showLoading() {
        dialog.show()
    }

    private fun hideLoading() {
        dialog.dismiss()
    }

    private fun displayNoContent() {
        rvStudents.visibility = View.GONE
        tvNoContent.visibility = View.VISIBLE
    }

    private fun getStudents() {
        context!!.doRequest({ ParentController.getStudents(context!!, context!!.getCurrentUserId()) },
            { showLoading() },
            {
                hideLoading()
                if (it.students!!.isEmpty()) {
                    displayNoContent()
                } else {
                    rvStudents.visibility = View.VISIBLE
                    tvNoContent.visibility = View.GONE
                    adapter.list = it.students
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