package io.github.sainiharry.shot.feature.photoslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.github.sainiharry.shot.common.ImageSource
import io.github.sainiharry.shot.common.Photo
import io.github.sainiharry.shot.feature.basefeature.Event
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
class PhotoListViewModelTest {

    @Mock
    private lateinit var photoRepository: PhotoRepository

    @Mock
    private lateinit var photoListObserver: Observer<List<Photo>>

    @Mock
    private lateinit var photoDetailNavigationEventObserver: Observer<Event<Photo>>

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var model: PhotoListViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        model = PhotoListViewModel(photoRepository, testCoroutineDispatcher)
        model.photoList.observeForever(photoListObserver)
        model.photoDetailsNavigationEvent.observeForever(photoDetailNavigationEventObserver)
    }

    @After
    fun tearDown() {
        model.photoList.removeObserver(photoListObserver)
        model.photoDetailsNavigationEvent.removeObserver(photoDetailNavigationEventObserver)
    }

    @Test
    fun testLoadData() = runBlockingTest {
        val mockPhotoList = listOf(getMockPhoto())
        `when`(photoRepository.fetchPhotos(ImageSource.UNSPLASH)).thenReturn(mockPhotoList)
        model.loadData()

        verify(photoListObserver).onChanged(mockPhotoList)
        verifyNoMoreInteractions(photoListObserver)
    }

    @Test
    fun testItemClickListener() {
        val photo = getMockPhoto()
        model.onItemClick(photo)
        verify(photoDetailNavigationEventObserver).onChanged(Event(photo))
        verifyNoMoreInteractions(photoDetailNavigationEventObserver)
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