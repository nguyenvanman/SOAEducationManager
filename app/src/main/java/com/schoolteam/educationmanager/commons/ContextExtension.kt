package com.schoolteam.educationmanager.commons

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.schoolteam.educationmanager.activities.LoginActivity
import io.reactivex.Observable
import retrofit2.Response

@SuppressLint("CheckResult")
fun <T : Any> Context.doRequest(
    observableCreator: () -> Observable<Response<T>>,
    loadingAction: (() -> Unit)? = null,
    successAction: (T) -> Unit,
    failureAction: ((String) -> Unit)? = null,
    requestErrorAction: ((String?) -> Unit)? = null
) {
    loadingAction?.invoke()
    observableCreator.invoke().subscribe({
        when {
            it.code() in 200..300 -> successAction.invoke(it.body()!!)
            it.code() == 401 -> {
                clearLoginInformation()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            else -> failureAction?.invoke(it.errorBody().toString())
        }
    }, {
        requestErrorAction?.invoke(it.message)
    })
}