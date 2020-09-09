package io.github.sainiharry.shot.network

import io.github.sainiharry.shot.common.ApiHeaders
import okhttp3.Interceptor
import okhttp3.Response

private const val UNSPLASH_AUTHORIZATION_HEADER = "Authorization"

class ApiKeyInterceptor(private val unsplashClientId: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalRequestBuilder = originalRequest.newBuilder()
        if (originalRequest.header(
                ApiHeaders.UNSPLASH_AUTHENTICATION_HEADER.substringBefore(
                    ApiHeaders.HEADER_VALUE_SEPARATOR
                )
            ) != null
        ) {
            originalRequestBuilder.addHeader(UNSPLASH_AUTHORIZATION_HEADER, "Client-ID $unsplashClientId")
                .removeHeader(ApiHeaders.UNSPLASH_AUTHENTICATION_HEADER)
        }

        return chain.proceed(originalRequestBuilder.build())
    }
}