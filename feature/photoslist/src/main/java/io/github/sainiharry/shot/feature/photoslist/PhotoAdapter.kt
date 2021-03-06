package io.github.sainiharry.shot.feature.photoslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.sainiharry.shot.common.ItemClickListener
import io.github.sainiharry.shot.common.Photo
import kotlinx.android.synthetic.main.item_photo.view.*

internal val listDiffer = object : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
}

internal class PhotoAdapter(
    private val itemClickListener: ItemClickListener<Photo>,
    private val clickedItemViewListener: (view: View) -> Unit
) :
    ListAdapter<Photo, PhotosViewHolder>(listDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder =
        PhotosViewHolder(parent, itemClickListener, clickedItemViewListener)

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

internal class PhotosViewHolder(
    parent: ViewGroup,
    private val itemClickListener: ItemClickListener<Photo>,
    private val clickedItemViewListener: (view: View) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
) {

    private val photosImageView = itemView.findViewById<ImageView>(R.id.photo)

    private var photo: Photo? = null

    private val defaultItemHeight =
        itemView.context.resources.getDimensionPixelSize(R.dimen.item_photo_height)

    init {
        itemView.setOnClickListener { view ->
            clickedItemViewListener(view)
            photo?.let {
                itemClickListener.onItemClick(it)
            }
        }
    }

    fun onBind(photo: Photo) {
        this.photo = photo
        itemView.photo.layoutParams.height = photo.height?.let {
            (it / 10).toInt()
        } ?: defaultItemHeight
        ViewCompat.setTransitionName(itemView.root_view, photo.id)
        Glide.with(itemView.context)
            .load(photo.url)
            .centerCrop()
            .into(photosImageView)
    }
}
