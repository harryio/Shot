package io.github.sainiharry.shot.feature.photoslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
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

    private var clickedItemView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)

        model.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        return inflater.inflate(R.layout.fragment_photo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        if (!this::adapter.isInitialized) {
            adapter = PhotoAdapter(model) {
                clickedItemView = it
            }
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

        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                startPostponedEnterTransition()
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        model.photoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        model.photoDetailsNavigationEvent.observe(viewLifecycleOwner, EventObserver { photo ->
            val extras: FragmentNavigator.Extras? = if (clickedItemView != null) {
                val transitionName = ViewCompat.getTransitionName(clickedItemView!!) ?: ""
                FragmentNavigatorExtras(clickedItemView!! to transitionName)
            } else {
                null
            }

            if (extras != null) {
                navController.navigate(
                    PhotoListFragmentDirections.actionPhotoDetails(
                        photo.id,
                        photo.title ?: getString(R.string.photo_details),
                        photo.url
                    ), extras
                )
            } else {
                navController.navigate(
                    PhotoListFragmentDirections.actionPhotoDetails(
                        photo.id,
                        photo.title ?: getString(R.string.photo_details),
                        photo.url
                    )
                )
            }
        })

        model.errorEvent.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), io.github.sainiharry.shot.feature.photodetail.R.string.generic_error, Toast.LENGTH_SHORT).show()
        })
    }
}