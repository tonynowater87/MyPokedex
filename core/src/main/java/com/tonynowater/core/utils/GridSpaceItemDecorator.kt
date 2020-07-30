package com.tonynowater.core.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpaceItemDecorator(private val space: Int, private val paddingTop: Boolean = false) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val layoutManager = parent.layoutManager as? GridLayoutManager ?: return

        val position = parent.getChildAdapterPosition(view)
        val spanCount = layoutManager.spanCount
        val surplus = position % spanCount

        // Timber.d("[DEBUG] [position] => $position, [spanCount] => $spanCount, [surplus] => $surplus")

        when(surplus) {
            0 -> { // most left item
                outRect.left = space
            }
            spanCount - 1 -> { // most right item
                outRect.left = space
                outRect.right = space
            }
            else -> { // others
                outRect.left = space
            }
        }

        if (paddingTop && position < spanCount) {
            // not first row
            outRect.top = space
        } else if (position >= spanCount) {
            outRect.top = space
        }
    }
}