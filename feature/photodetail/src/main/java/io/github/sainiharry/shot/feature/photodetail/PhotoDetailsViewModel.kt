package io.github.sainiharry.shot.feature.photodetail

import androidx.lifecycle.ViewModel
import io.github.sainiharry.shot.repository.photos.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher

internal class PhotoDetailsViewModel(
    private val photoRepository: PhotoRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {



}