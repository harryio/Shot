package io.github.sainiharry.shot.feature.photoslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.github.sainiharry.shot.feature.basefeature.EventObserver
import kotlinx.android.synthetic.main.fragment_photo_list.*
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.get

class PhotoListFragment : Fragment() {

    private val model by viewModels<PhotoListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PhotoListViewModel(get(), Dispatchers.Main.immediate) as T
            }
        }
    }

    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        if (!this::adapter.isInitialized) {
            adapter = PhotoAdapter(model)
        }

        val spanCount = 2
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager =
            StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        recycler_view.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount,
                requireContext().resources.getDimensionPixelSize(R.dimen.spacing_s)
            )
        )
        recycler_view.adapter = this@PhotoListFragment.adapter

        model.photoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        model.photoDetailsNavigationEvent.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(
                PhotoListFragmentDirections.actionPhotoDetails(
                    it.id,
                    it.title ?: getString(R.string.photo_details),
                    it.url
                )
            )
        })
    }
}