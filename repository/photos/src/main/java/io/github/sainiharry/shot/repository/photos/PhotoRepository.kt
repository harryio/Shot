package io.github.sainiharry.shot.repository.photos

import io.github.sainiharry.shot.common.ImageSource
import io.github.sainiharry.shot.common.Photo

interface PhotoRepository {

    suspend fun fetchPhotos(imageSource: ImageSource): List<Photo>
}

internal class PhotoRepositoryImpl(private val photosService: PhotosService) : PhotoRepository {

    override suspend fun fetchPhotos(imageSource: ImageSource): List<Photo> {
        return photosService.fetchUnSplashPhotos()
            .mapNotNull { it.toPhoto() }
    }
}