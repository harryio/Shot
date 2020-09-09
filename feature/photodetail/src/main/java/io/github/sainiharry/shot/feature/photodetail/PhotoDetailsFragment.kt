package io.github.sainiharry.shot.feature.photodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}