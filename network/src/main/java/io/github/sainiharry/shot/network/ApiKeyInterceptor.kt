package io.github.sainiharry.shot.network

import okhttp3.Interceptor
import okhttp3.Response

const val UNSPLASH_AUTHENTICATION_HEADER = "UnSplash-Authentication"

private const val UNSPLASH_AUTHORIZATION_HEADER = "Authorization: Client-ID"

class ApiKeyInterceptor(private val unsplashClientId: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalRequestBuilder = originalRequest.newBuilder()
        if (originalRequest.header(UNSPLASH_AUTHENTICATION_HEADER) != null) {
            originalRequestBuilder.addHeader(UNSPLASH_AUTHORIZATION_HEADER, unsplashClientId)
        }

        return chain.proceed(originalRequestBuilder.build())
    }
}