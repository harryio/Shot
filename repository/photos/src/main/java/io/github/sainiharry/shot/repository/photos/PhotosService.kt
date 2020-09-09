package io.github.sainiharry.shot.repository.photos

import io.github.sainiharry.shot.common.ApiHeaders
import retrofit2.http.GET
import retrofit2.http.Headers

internal interface PhotosService {

    @Headers(ApiHeaders.UNSPLASH_AUTHENTICATION_HEADER)
    @GET("/photos")
    suspend fun fetchUnSplashPhotos(): List<UnsplashPhotoResponse>
}