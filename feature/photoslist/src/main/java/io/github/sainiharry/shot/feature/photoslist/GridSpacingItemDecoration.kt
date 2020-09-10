package io.github.sainiharry.shot.feature.photoslist

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if (column == 0) {
            outRect.left = spacing
        } else {
            outRect.left = spacing / 2
        }

        if (column == (spanCount - 1)) {
            outRect.right = spacing
        } else {
            outRect.right = spacing / 2
        }

        if (position < spanCount) {
            outRect.top = spacing
        }
        outRect.bottom = spacing
    }
}