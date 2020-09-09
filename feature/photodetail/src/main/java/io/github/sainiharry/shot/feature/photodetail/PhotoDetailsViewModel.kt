package io.github.sainiharry.shot.feature.photodetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.sainiharry.shot.common.ImageSource
import io.github.sainiharry.shot.common.Photo
import io.github.sainiharry.shot.repository.photos.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

private const val DEFAULT_VAL = "--"

internal class PhotoDetailsViewModel(
    private val photoRepository: PhotoRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val photographer = MutableLiveData(DEFAULT_VAL)

    val date = MutableLiveData(DEFAULT_VAL)

    val location = MutableLiveData(DEFAULT_VAL)

    val likes = MutableLiveData(DEFAULT_VAL)

    fun handlePhoto(photo: Photo?) {
        photo?.let {
            viewModelScope.launch(dispatcher) {
                val photoDetails = photoRepository.fetchPhotoDetails(ImageSource.UNSPLASH, photo.id)
                photoDetails?.let {
                    photographer.value = it.photographer ?: DEFAULT_VAL
                    date.value = it.date ?: DEFAULT_VAL
                    location.value = it.location ?: DEFAULT_VAL
                    likes.value = it.likes ?: DEFAULT_VAL
                }
            }
        }
    }
}