package com.schoolteam.educationmanager.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.commons.doRequest
import com.schoolteam.educationmanager.commons.getCurrentUserId
import com.schoolteam.educationmanager.commons.showLoadingDialog
import com.schoolteam.educationmanager.controllers.UserController
import com.schoolteam.educationmanager.models.dtos.requests.UserInfo
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat

class ProfileActivity : AppCompatActivity() {
    private lateinit var loadingDialog: Dialog
    private var isEditing: Boolean = false
        set(value) {
            field = value
            invalidateOptionsMenu()
            changeEditTextState(value)
        }

    private var userInfo: com.schoolteam.educationmanager.models.dtos.responses.UserInfo? = null
        set(value) {
            field = value
            displayUserInfo(value!!)
        }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        loadingDialog = showLoadingDialog().apply { dismiss() }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        isEditing = false

        doRequest({ UserController.getUserInfo(this, getCurrentUserId()) },
            { loadingDialog.show() },
            {
                loadingDialog.dismiss()
                userInfo = it
            },
            { onError() },
            { onError() })
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun displayUserInfo(userInfo: com.schoolteam.educationmanager.models.dtos.responses.UserInfo) {
        tvName.text = userInfo.firstName
        tvFullName.text = "${userInfo.lastName} ${userInfo.firstName}"
        tvGender.text = getString(if (userInfo.gender!!) R.string.male else R.string.female)
        tvBirthDay.text = SimpleDateFormat("dd/MM/yyyy").format(userInfo.dateOfBirth)
        tvId.text = userInfo.identificationNumber
        edtPhoneNumber.setText(userInfo.phoneNumber)
        edtAddress.setText(userInfo.address)
        Glide.with(this).load(userInfo.avatarUrl).into(imgAvatar)
    }

    private fun changeEditTextState(isEditing: Boolean) {
        edtPhoneNumber.isEnabled = isEditing
        edtAddress.isEnabled = isEditing
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_profile_menu, menu)
        menu.getItem(0).isVisible = !isEditing
        menu.getItem(1).isVisible = isEditing
        menu.getItem(2).isVisible = isEditing
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                isEditing = true
                true
            }
            R.id.action_save -> {
                doUpdating()
                true
            }
            R.id.action_cancel -> {
                isEditing = false
                displayUserInfo(userInfo!!)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun doUpdating() {
        val phoneNumber = edtPhoneNumber.text.toString()
        val address = edtAddress.text.toString()
        if (phoneNumber.isEmpty() || address.isEmpty()) {
            toast(R.string.fill_all_fields)
            return
        }
        doRequest({ UserController.updateUserInfo(this, getCurrentUserId(), UserInfo(phoneNumber, address)) },
            { loadingDialog.show() },
            {
                toast(R.string.update_success)
                userInfo = it
                isEditing = false
                loadingDialog.dismiss()
            },
            { onError() },
            { onError() })
    }

    private fun onError() {
        toast(R.string.error_occurred)
        loadingDialog.dismiss()
    }
}
