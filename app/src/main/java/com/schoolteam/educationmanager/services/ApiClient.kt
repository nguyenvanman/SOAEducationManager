package com.schoolteam.educationmanager.services

import com.schoolteam.educationmanager.commons.ApiBaseUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null

    private var builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(ApiBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    private val httpClient = OkHttpClient.Builder()

    fun <S> createService(serviceClass: Class<S>): S {
        return createService(serviceClass, null)
    }

    fun <S> createService(serviceClass: Class<S>, authToken: Map<String, String>?): S {
        if (authToken != null) {
            val interceptor = AuthenticationInterceptor(authToken)
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)
            }
        }
        else {
            httpClient.interceptors().clear()
        }

        builder.client(httpClient.build())
        retrofit = builder.build()
        return retrofit!!.create(serviceClass)

    }

    class AuthenticationInterceptor(private val authToken: Map<String, String>) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val builder = original.newBuilder()
            for (key in authToken.keys) {
                builder.header(key, authToken.getValue(key))
            }
            val request = builder.build()
            return chain.proceed(request);
        }
    }
}
