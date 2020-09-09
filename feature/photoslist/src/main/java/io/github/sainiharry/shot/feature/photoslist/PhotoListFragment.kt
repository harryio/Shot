package io.github.sainiharry.shot.feature.photoslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import io.github.sainiharry.shot.repository.photos.PhotoRepository
import kotlinx.android.synthetic.main.fragment_photo_list.*
import kotlinx.coroutines.Dispatchers

class PhotoListFragment : Fragment() {

    // TODO: 09/09/20 initialize
    private lateinit var photoRepository: PhotoRepository

    private val model by viewModels<PhotoListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PhotoListViewModel(photoRepository, Dispatchers.Main.immediate) as T
            }
        }
    }

    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!this::adapter.isInitialized) {
            adapter = PhotoAdapter(model)
        }

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler_view.adapter = this@PhotoListFragment.adapter

        model.photoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}