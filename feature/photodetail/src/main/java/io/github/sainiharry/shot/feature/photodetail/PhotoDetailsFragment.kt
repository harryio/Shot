package io.github.sainiharry.shot.feature.photodetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import io.github.sainiharry.shot.common.Photo
import io.github.sainiharry.shot.feature.photodetail.databinding.FragmentPhotoDetailsBinding
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.get

class PhotoDetailsFragment : Fragment() {

    private val model by viewModels<PhotoDetailsViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PhotoDetailsViewModel(get(), Dispatchers.Main.immediate) as T
            }
        }
    }

    private lateinit var binding: FragmentPhotoDetailsBinding

    private var photo: Photo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
            containerColor = Color.WHITE
        }

        photo = arguments?.let {
            val id = it.getString("photoId")
            val title = it.getString("photoTitle") ?: ""
            val url = it.getString("photoUrl") ?: ""

            if (id == null) {
                Toast.makeText(requireContext(), "Id not found", Toast.LENGTH_SHORT).show()
                null
            } else {
                Photo(id, title, url)
            }
        }

        model.handlePhoto(photo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        binding.model = model
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(binding.rootView, photo?.id ?: "")
        val navController = findNavController()

        Glide.with(requireActivity())
            .load(photo?.url)
            .into(binding.photoImageView)

        photo?.let {
            binding.toolbar.title = it.title
        }
        NavigationUI.setupWithNavController(binding.toolbar, navController)
    }
}