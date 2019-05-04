package com.schoolteam.educationmanager.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.commons.clearLoginInformation
import com.schoolteam.educationmanager.commons.doRequest
import com.schoolteam.educationmanager.commons.getCurrentUserId
import com.schoolteam.educationmanager.commons.showLoadingDialog
import com.schoolteam.educationmanager.controllers.AuthenticationController
import com.schoolteam.educationmanager.models.dtos.requests.Password
import kotlinx.android.synthetic.main.activity_change_password.*
import org.jetbrains.anko.toast

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val input = mapOf(
            edt_current_password to tilCurrentPassword,
            edt_new_password to tilNewPassword,
            edt_confirm_new_password to tilConfirmNewPassword
        )
        btn_confirm.setOnClickListener {
            var isValidData = true
            input.forEach { pair ->
                pair.value.error = null
                if (pair.key.text.isNullOrEmpty()) {
                    pair.value.error = getString(R.string.must_not_empty)
                    isValidData = false
                }
            }

            if (edt_new_password.text.toString() != edt_confirm_new_password.text.toString()) {
                tilConfirmNewPassword.error = getString(R.string.not_match)
                isValidData = false
            }

            if (isValidData) doChangePassword(edt_current_password.text.toString(), edt_new_password.text.toString())
        }
    }


    private fun doChangePassword(oldPassword: String, newPassword: String) {
        val dialog = showLoadingDialog()
        doRequest({
            AuthenticationController.changePassword(
                this,
                getCurrentUserId(),
                Password(oldPassword, newPassword)
            )
        },
            { dialog.show() },
            {
                dialog.dismiss()
                toast(R.string.change_password_success)
                clearLoginInformation()
                finish()
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("flag", true)
                }
                startActivity(intent)
            },
            {
                dialog.dismiss()
                tilCurrentPassword.error = getString(R.string.wrong_password)
            },
            {
                dialog.dismiss()
                toast(R.string.error_occurred)
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }
}