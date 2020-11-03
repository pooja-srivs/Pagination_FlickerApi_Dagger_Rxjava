package com.example.sociolla.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener(layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0 // The total number of items in the dataset after the last load

    private var loading = true // True if we are still waiting for the last set of data to load.

    private val visibleThreshold = 5 // The minimum amount of items to have below your current scroll position before loading more.

    var firstVisibleItem = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0

    private var current_page = 1

    private var mLinearLayoutManager: LinearLayoutManager


    init {
        mLinearLayoutManager = layoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }
    }

    abstract fun onLoadMore(current_page : Int)
}