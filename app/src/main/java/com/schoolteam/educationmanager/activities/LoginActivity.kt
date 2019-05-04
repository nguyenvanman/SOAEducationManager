package com.schoolteam.educationmanager.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.commons.Authorization
import com.schoolteam.educationmanager.commons.saveLoginInformation
import com.schoolteam.educationmanager.commons.showLoadingDialog
import com.schoolteam.educationmanager.controllers.AuthenticationController
import com.schoolteam.educationmanager.models.dtos.requests.LoginBody
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val input = mapOf(edt_username to tilUsername, edt_password to tilPassword)
        btn_login.setOnClickListener {
            var isValidData = true
            input.forEach { pair ->
                pair.value.error = null
                if (pair.key.text.isNullOrEmpty()) {
                    pair.value.error = getString(R.string.must_not_empty)
                    isValidData = false
                }
            }

            if (isValidData) doLogin()
        }
    }

    @SuppressLint("CheckResult")
    private fun doLogin() {
        val dialog = showLoadingDialog()

        AuthenticationController
            .login(LoginBody(edt_username.text.toString(), edt_password.text.toString()))
            .subscribe({response ->
                if (response.code() == 200) {
                    saveLoginInformation(response.body()!!, response.headers().get(Authorization)!!)
                    toast(R.string.login_success)
                    dialog.dismiss()
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    edt_password.text!!.clear()
                    tilPassword.error = getString(R.string.wrong_account)
                    toast(R.string.login_failed)
                    dialog.dismiss()
                }
            }, {
                dialog.dismiss()
                toast(R.string.error_occurred)
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (intent.hasExtra("flag")) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            finish()
        }
    }
}
