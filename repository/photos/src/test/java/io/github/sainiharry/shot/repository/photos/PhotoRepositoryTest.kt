package io.github.sainiharry.shot.repository.photos

import io.github.sainiharry.shot.common.ImageSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PhotoRepositoryTest {

    @Mock
    private lateinit var photosService: PhotosService

    private lateinit var photosRepository: PhotoRepository

    @Before
    fun setup() {
        photosRepository = PhotoRepositoryImpl(photosService)
    }

    @Test
    fun testFetchPhotos() = runBlockingTest {
        val unsplashPhotoResponse = getMockUnsplashPhotoResponse()
        `when`(photosService.fetchUnSplashPhotos(anyInt())).thenReturn(listOf(unsplashPhotoResponse))

        val photos = photosRepository.fetchPhotos(ImageSource.UNSPLASH)
        assertEquals(listOf(unsplashPhotoResponse.toPhoto()), photos)
    }

    @Test
    fun testInvalidPhotosResponse() = runBlockingTest {
        var unsplashPhotoResponse = getMockUnsplashPhotoResponse(null)
        `when`(photosService.fetchUnSplashPhotos(anyInt())).thenReturn(listOf(unsplashPhotoResponse))
        assertTrue(photosRepository.fetchPhotos(ImageSource.UNSPLASH).isEmpty())

        unsplashPhotoResponse = getMockUnsplashPhotoResponse(photos = null)
        `when`(photosService.fetchUnSplashPhotos(anyInt())).thenReturn(listOf(unsplashPhotoResponse))
        assertTrue(photosRepository.fetchPhotos(ImageSource.UNSPLASH).isEmpty())

        unsplashPhotoResponse = getMockUnsplashPhotoResponse(photos = getMockUnsplashPhoto(regular = null))
        `when`(photosService.fetchUnSplashPhotos(anyInt())).thenReturn(listOf(unsplashPhotoResponse))
        assertTrue(photosRepository.fetchPhotos(ImageSource.UNSPLASH).isEmpty())
    }

    @Test
    fun testFetchPhotoDetailsSuccess() = runBlockingTest {
        val id = "34"
        val response = getMockUnsplashPhotoResponse()
        `when`(photosService.fetchUnSplashPhoto(id)).thenReturn(response)

        assertEquals(response.toPhoto(), photosRepository.fetchPhotoDetails(ImageSource.UNSPLASH, id))
    }

    @Test
    fun testFetchPhotoDetailsFailure() = runBlockingTest {
        val id = "1"
        var response = getMockUnsplashPhotoResponse(null)
        `when`(photosService.fetchUnSplashPhoto(id)).thenReturn(response)
        assertNull(photosRepository.fetchPhotoDetails(ImageSource.UNSPLASH, id))

        response = getMockUnsplashPhotoResponse(photos = null)
        `when`(photosService.fetchUnSplashPhoto(id)).thenReturn(response)
        assertNull(photosRepository.fetchPhotoDetails(ImageSource.UNSPLASH, id))

        response = getMockUnsplashPhotoResponse(photos = getMockUnsplashPhoto(regular = null))
        `when`(photosService.fetchUnSplashPhoto(id)).thenReturn(response)
        assertNull(photosRepository.fetchPhotoDetails(ImageSource.UNSPLASH, id))
    }
}

private fun getMockUnsplashPhotoResponse(
    id: String? = "id",
    description: String? = "description",
    likes: Long? = 1,
    location: UnsplashPhotoLocation? = getMockUnsplashPhotoLocation(),
    user: UnsplashUser? = getMockUnsplashUser(),
    photos: UnsplashPhoto? = getMockUnsplashPhoto(),
    createdAt: Date? = Date(),
    height: Long? = 340
) = UnsplashPhotoResponse(
    id,
    description,
    likes,
    location,
    user,
    height,
    photos,
    createdAt
)

private fun getMockUnsplashPhotoLocation(city: String? = "Montreal") = UnsplashPhotoLocation(city)

private fun getMockUnsplashUser(name: String? = "John") = UnsplashUser(name)

private fun getMockUnsplashPhoto(
    raw: String? = null,
    full: String? = null,
    regular: String? = "regular",
    small: String? = null,
    thumb: String? = null
) = UnsplashPhoto(raw, full, regular, small, thumb)