package io.github.sainiharry.shot.repository.photos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.github.sainiharry.shot.common.Photo
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "MM/dd/yy"

internal object NetworkUtils {

    private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.US)

    fun formatDate(date: Date?): String = date?.let {
        dateFormatter.format(it)
    } ?: ""
}

@JsonClass(generateAdapter = true)
internal data class UnsplashPhotoResponse(
    val id: String?,
    val description: String?,
    val likes: Long?,
    val location: UnsplashPhotoLocation?,
    val user: UnsplashUser?,
    @Json(name = "urls") val photos: UnsplashPhoto?,
    @Json(name = "created_at") val createdAt: Date?
)

@JsonClass(generateAdapter = true)
internal data class UnsplashPhoto(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?
)

@JsonClass(generateAdapter = true)
internal data class UnsplashPhotoLocation(val city: String?)

@JsonClass(generateAdapter = true)
internal data class UnsplashUser(val name: String?)

internal fun UnsplashPhotoResponse.toPhoto(): Photo? = when {
    id == null -> null
    photos == null -> null
    photos.regular == null -> null
    else -> Photo(
        id,
        description,
        photos.regular,
        user?.name,
        NetworkUtils.formatDate(createdAt),
        location?.city,
        likes?.toString()
    )
}