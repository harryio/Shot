package io.github.sainiharry.shot.common

private const val BASE_UNSPLASH_API_URL = "https://api.unsplash.com/"

enum class ImageSource constructor(val apiUrl: String) {

    UNSPLASH(BASE_UNSPLASH_API_URL)
}
