package io.github.sainiharry.shot.repository.photos

import io.github.sainiharry.shot.common.ImageSource
import io.github.sainiharry.shot.common.Photo

interface PhotosRepository {

    suspend fun fetchPhotos(imageSource: ImageSource): List<Photo>
}

internal class PhotosRepositoryImpl(private val photosService: PhotosService) : PhotosRepository {

    override suspend fun fetchPhotos(imageSource: ImageSource): List<Photo> {
        return photosService.fetchUnSplashPhotos()
            .mapNotNull { it.toPhoto() }
    }
}