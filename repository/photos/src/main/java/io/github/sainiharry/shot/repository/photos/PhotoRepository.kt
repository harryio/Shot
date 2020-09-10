package io.github.sainiharry.shot.repository.photos

import io.github.sainiharry.shot.common.ImageSource
import io.github.sainiharry.shot.common.Photo
import io.github.sainiharry.shot.network.NetworkInteractor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val photoRepositoryModule = module {

    single<PhotoRepository> {
        val unSplashPhotosService = get<PhotosService> { parametersOf(ImageSource.UNSPLASH) }
        PhotoRepositoryImpl(unSplashPhotosService)
    }

    factory { (imageSource: ImageSource) ->
        get<NetworkInteractor>().getRetrofit(imageSource).create(PhotosService::class.java)
    }
}

private const val PER_PAGE = 20

interface PhotoRepository {

    suspend fun fetchPhotos(imageSource: ImageSource): List<Photo>

    suspend fun fetchPhotoDetails(imageSource: ImageSource, id: String): Photo?
}

internal class PhotoRepositoryImpl(private val unSplashPhotosService: PhotosService) :
    PhotoRepository {


    override suspend fun fetchPhotos(imageSource: ImageSource): List<Photo> {
        return unSplashPhotosService.fetchUnSplashPhotos(PER_PAGE)
            .mapNotNull { it.toPhoto() }
    }

    override suspend fun fetchPhotoDetails(imageSource: ImageSource, id: String): Photo? {
        return unSplashPhotosService.fetchUnSplashPhoto(id).toPhoto()
    }
}