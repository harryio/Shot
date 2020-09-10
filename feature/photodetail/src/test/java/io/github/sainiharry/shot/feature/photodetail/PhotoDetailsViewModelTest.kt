package io.github.sainiharry.shot.feature.photodetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.github.sainiharry.shot.common.ImageSource
import io.github.sainiharry.shot.common.Photo
import io.github.sainiharry.shot.repository.photos.PhotoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PhotoDetailsViewModelTest {

    @Mock
    private lateinit var photoRepository: PhotoRepository

    @Mock
    private lateinit var photographObserver: Observer<String>

    @Mock
    private lateinit var dateObserver: Observer<String>

    @Mock
    private lateinit var locationObserver: Observer<String>

    @Mock
    private lateinit var likesObserver: Observer<String>

    private lateinit var model: PhotoDetailsViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        model = PhotoDetailsViewModel(photoRepository, testCoroutineDispatcher)
        model.photographer.observeForever(photographObserver)
        model.date.observeForever(dateObserver)
        model.location.observeForever(locationObserver)
        model.likes.observeForever(likesObserver)

        verify(photographObserver).onChanged(DEFAULT_VAL)
        verify(dateObserver).onChanged(DEFAULT_VAL)
        verify(locationObserver).onChanged(DEFAULT_VAL)
        verify(likesObserver).onChanged(DEFAULT_VAL)
    }

    @After
    fun tearDown() {
        model.photographer.removeObserver(photographObserver)
        model.date.removeObserver(dateObserver)
        model.location.removeObserver(locationObserver)
        model.likes.removeObserver(likesObserver)
    }

    @Test
    fun testHandlePhotoWithoutPhoto() {
        model.handlePhoto(null)
        verifyNoMoreInteractions(photographObserver)
        verifyNoMoreInteractions(dateObserver)
        verifyNoMoreInteractions(locationObserver)
        verifyNoMoreInteractions(likesObserver)
    }

    @Test
    fun testFetchPhotoDetailsWithNullPhoto() = runBlockingTest {
        val photo = getMockPhoto()
        `when`(photoRepository.fetchPhotoDetails(ImageSource.UNSPLASH, photo.id)).thenReturn(null)
        model.handlePhoto(photo)
        verifyNoMoreInteractions(photographObserver)
        verifyNoMoreInteractions(dateObserver)
        verifyNoMoreInteractions(locationObserver)
        verifyNoMoreInteractions(likesObserver)
    }

    @Test
    fun testFetchPhotoDetailsWithEmptyPhotoDetails() = runBlockingTest {
        val photo = getMockPhoto()
        `when`(photoRepository.fetchPhotoDetails(ImageSource.UNSPLASH, photo.id)).thenReturn(photo)
        model.handlePhoto(photo)
        verify(photographObserver, times(2)).onChanged(DEFAULT_VAL)
        verify(dateObserver, times(2)).onChanged(DEFAULT_VAL)
        verify(locationObserver, times(2)).onChanged(DEFAULT_VAL)
        verify(likesObserver, times(2)).onChanged(DEFAULT_VAL)
        verifyNoMoreInteractions(photographObserver)
        verifyNoMoreInteractions(dateObserver)
        verifyNoMoreInteractions(locationObserver)
        verifyNoMoreInteractions(likesObserver)
    }

    @Test
    fun testFetchPhotoDetailsSuccess() = runBlockingTest {
        val photo = getMockPhoto(photographer = "photographer", date = "date", location = "location", likes = "likes")
        `when`(photoRepository.fetchPhotoDetails(ImageSource.UNSPLASH, photo.id)).thenReturn(photo)
        model.handlePhoto(photo)
        verify(photographObserver).onChanged(photo.photographer)
        verify(dateObserver).onChanged(photo.date)
        verify(locationObserver).onChanged(photo.location)
        verify(likesObserver).onChanged(photo.likes)
        verifyNoMoreInteractions(photographObserver)
        verifyNoMoreInteractions(dateObserver)
        verifyNoMoreInteractions(locationObserver)
        verifyNoMoreInteractions(likesObserver)
    }

    private fun getMockPhoto(
        id: String = "id",
        title: String? = null,
        url: String = "url",
        photographer: String? = null,
        date: String? = null,
        location: String? = null,
        likes: String? = null,
        height: Long? = null
    ) = Photo(id, title, url, photographer, date, location, likes, height)
}