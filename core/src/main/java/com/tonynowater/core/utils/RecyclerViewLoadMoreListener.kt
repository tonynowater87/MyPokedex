package com.tonynowater.core.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoadMoreListener(
    recyclerView: RecyclerView,
    private var loadMoreImpl: LoadMoreInterface
) : RecyclerView.OnScrollListener() {

    private var threshold = 8

    init {
        recyclerView.addOnScrollListener(this)
    }

    /** DRAGGING -> SETTLING -> IDLE */
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager

        layoutManager?.let {

            val itemCount = it.itemCount

            val lastVisiblePosition = when (layoutManager) {
                is LinearLayoutManager -> layoutManager.findLastCompletelyVisibleItemPosition()
                is GridLayoutManager -> layoutManager.findLastCompletelyVisibleItemPosition()
                else -> return
            }

            if (loadMoreImpl.isLoading()) {
                return
            }

            if (loadMoreImpl.isError()) {
                if (lastVisiblePosition + threshold < itemCount) {
                    // reset to no error state, when scroll upward over threshold
                    loadMoreImpl.reset()
                }
            } else if (lastVisiblePosition + threshold >= itemCount) {
                // only load more when these is no error
                loadMoreImpl.loadMore()
            }
        }
    }
}

interface LoadMoreInterface {
    fun loadMore()
    fun isLoading(): Boolean
    fun isError(): Boolean
    fun reset()
}