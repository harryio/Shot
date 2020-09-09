package io.github.sainiharry.shot.repository.photos

import com.squareup.moshi.Json

internal data class UnsplashPhotoResponse(@Json(name = "urls") val photos: UnsplashPhoto?)

internal data class UnsplashPhoto(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?
)