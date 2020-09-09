package io.github.sainiharry.shot.network

import io.github.sainiharry.shot.common.ApiHeaders
import okhttp3.Interceptor
import okhttp3.Response

const val UNSPLASH_AUTHENTICATION_HEADER = "UnSplash-Authentication"

class ApiKeyInterceptor(private val unsplashClientId: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalRequestBuilder = originalRequest.newBuilder()
        if (originalRequest.header(UNSPLASH_AUTHENTICATION_HEADER) != null) {
            originalRequestBuilder.addHeader(ApiHeaders.UNSPLASH_AUTHENTICATION_HEADER, unsplashClientId)
        }

        return chain.proceed(originalRequestBuilder.build())
    }
}