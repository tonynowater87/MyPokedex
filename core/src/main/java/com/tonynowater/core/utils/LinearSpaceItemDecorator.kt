package com.tonynowater.core.utils

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView


class LinearSpaceItemDecorator(
    private val orientation: Int,
    private val space: Int
) : RecyclerView.ItemDecoration() {

    companion object {
        const val VERTICAL = LinearLayout.VERTICAL
        const val HORIZONTAL = LinearLayout.HORIZONTAL
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) return // last item or only one item

        if (orientation == VERTICAL) {
            outRect.bottom = space
        } else if (orientation == HORIZONTAL) {
            outRect.right = space
        }
    }
}