package io.github.sainiharry.shot.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import io.github.sainiharry.shot.common.ImageSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import kotlin.collections.HashMap

const val UNSPLASH_API_KEY_QUALIFIER = "unsplash_api_key"

val networkModule = module {
    single {
        NetworkInteractor(get(named(UNSPLASH_API_KEY_QUALIFIER)))
    }
}

class NetworkInteractor internal constructor(private val unsplashClientId: String) {

    private val retrofitMap = HashMap<ImageSource, Retrofit>()

    private val moshi by lazy {
        Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(unsplashClientId))
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()
    }

    fun getRetrofit(imageSource: ImageSource): Retrofit {
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