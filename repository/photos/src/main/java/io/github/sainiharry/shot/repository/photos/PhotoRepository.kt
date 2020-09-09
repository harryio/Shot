package io.github.sainiharry.shot.repository.photos

import io.github.sainiharry.shot.common.ImageSource
import io.github.sainiharry.shot.common.Photo
import io.github.sainiharry.shot.network.NetworkInteractor
import org.koin.dsl.module

val photoRepositoryModule = module {

    single<PhotoRepository> {
        val networkInteractor = get<NetworkInteractor>()
        PhotoRepositoryImpl(networkInteractor)
    }

}

private const val PER_PAGE = 20

interface PhotoRepository {

    suspend fun fetchPhotos(imageSource: ImageSource): List<Photo>
}

internal class PhotoRepositoryImpl(private val networkInteractor: NetworkInteractor) :
    PhotoRepository {


    override suspend fun fetchPhotos(imageSource: ImageSource): List<Photo> {
        return getService(imageSource).fetchUnSplashPhotos(PER_PAGE)
            .mapNotNull { it.toPhoto() }
    }

    private fun getService(imageSource: ImageSource): PhotosService =
        networkInteractor.getRetrofit(imageSource).create(PhotosService::class.java)
}