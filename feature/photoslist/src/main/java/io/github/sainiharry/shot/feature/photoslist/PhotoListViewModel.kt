package io.github.sainiharry.shot.feature.photoslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.sainiharry.shot.common.ImageSource
import io.github.sainiharry.shot.common.ItemClickListener
import io.github.sainiharry.shot.common.Photo
import io.github.sainiharry.shot.repository.photos.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

internal class PhotoListViewModel(
    private val photoRepository: PhotoRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(),
    ItemClickListener<Photo> {

    private val _photoList = MutableLiveData<List<Photo>>()
    val photoList: LiveData<List<Photo>>
        get() = _photoList

    fun loadData() {
        viewModelScope.launch(dispatcher) {
            val fetchPhotos = photoRepository.fetchPhotos(ImageSource.UNSPLASH)
            _photoList.value = fetchPhotos
        }
    }

    override fun onItemClick(obj: Photo) {
        TODO("Not yet implemented")
    }
}