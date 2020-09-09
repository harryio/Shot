package io.github.sainiharry.shot.repository.photos

import io.github.sainiharry.shot.common.ApiHeaders
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface PhotosService {

    @Headers(ApiHeaders.UNSPLASH_AUTHENTICATION_HEADER)
    @GET("/photos")
    suspend fun fetchUnSplashPhotos(@Query("per_page") perPage: Int): List<UnsplashPhotoResponse>

    @Headers(ApiHeaders.UNSPLASH_AUTHENTICATION_HEADER)
    @GET("/photos/{id}")
    suspend fun fetchUnSplashPhoto(@Path("id") id: String): UnsplashPhotoResponse
}