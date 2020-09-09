package io.github.sainiharry.shot.repository.photos

import com.squareup.moshi.Json
import io.github.sainiharry.shot.common.Photo

internal data class UnsplashPhotoResponse(@Json(name = "urls") val photos: UnsplashPhoto?)

internal data class UnsplashPhoto(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?
)

internal fun UnsplashPhotoResponse.toPhoto(): Photo? = when {
    photos == null -> null
    photos.regular == null -> null
    else -> Photo(photos.regular)
}