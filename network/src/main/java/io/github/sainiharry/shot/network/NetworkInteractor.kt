package io.github.sainiharry.shot.network

import com.squareup.moshi.Moshi
import io.github.sainiharry.shot.common.ImageSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class NetworkInteractor internal constructor(private val unsplashClientId: String) {

    private val retrofitMap = HashMap<ImageSource, Retrofit>()

    private val moshi by lazy {
        Moshi.Builder().build()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(unsplashClientId))
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()
    }

    internal fun getRetrofit(imageSource: ImageSource): Retrofit {
        val retrofit = retrofitMap[imageSource]
        return retrofit
            ?: Retrofit.Builder().baseUrl(imageSource.apiUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build().also {
                    retrofitMap[imageSource] = it
                }
    }
}