package io.github.sainiharry.shot.common

data class Photo(
    val id: String,
    val title: String?,
    val url: String,
    val photographer: String?,
    val date: String?,
    val location: String?,
    val likes: String?,
    val height: Long?
) {
    constructor(id: String, title: String, url: String) : this(id, title, url, null, null, null, null, null)
}