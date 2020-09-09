package io.github.sainiharry.shot.repository.photos

import com.squareup.moshi.Json
import io.github.sainiharry.shot.common.Photo

internal data class UnsplashPhotoResponse(
    val id: String?,
    @Json(name = "urls") val photos: UnsplashPhoto?
)

internal data class UnsplashPhoto(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?
)

internal fun UnsplashPhotoResponse.toPhoto(): Photo? = when {
    id == null -> null
    photos == null -> null
    photos.regular == null -> null
    else -> Photo(id, photos.regular)
}