package io.github.sainiharry.shot.feature.photoslist

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.sainiharry.shot.repository.photos.PhotoRepository

class PhotoListFragment : Fragment() {

    // TODO: 09/09/20 initialize
    private lateinit var photoRepository: PhotoRepository

    private val model by viewModels<PhotoListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PhotoListViewModel(photoRepository) as T
            }
        }
    }
}