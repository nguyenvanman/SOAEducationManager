package com.schoolteam.educationmanager.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.widget.ProgressBar
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.commons.PreferenceKeyUserId
import com.schoolteam.educationmanager.commons.saveLoginInformation
import com.schoolteam.educationmanager.controllers.AuthenticationController
import com.schoolteam.educationmanager.models.dtos.requests.LoginBody
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var input = mapOf(edt_username to tilUsername, edt_password to tilPassword)
        btn_login.setOnClickListener {
            var isValidData = true
            input.forEach { pair ->
                pair.value.error = null
                if (pair.key.text.isNullOrEmpty()) {
                    pair.value.error = "Field must be not empty!"
                    isValidData = false
                }
            }

            if (isValidData) doLogin()
        }
    }

    @SuppressLint("CheckResult")
    private fun doLogin() {
        var dialog = Dialog(this).apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            setCancelable(false)
            setContentView(R.layout.querying_progress)
            show()
        }

        AuthenticationController
            .login(LoginBody(edt_username.text.toString(), edt_password.text.toString()))
            .subscribe({response ->
                if (response.code() == 200) {
                    saveLoginInformation(response.body()!!, response.headers().get("Authorization")!!)
                    toast("Login success")
                    dialog.dismiss()
                } else {
                    edt_password.text!!.clear()
                    tilPassword.error = "Incorrect username or password!"
                    toast("Login failed")
                    dialog.dismiss()
                }
            }, { _ ->

            })
    }
}
