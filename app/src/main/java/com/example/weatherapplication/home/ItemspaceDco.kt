package com.example.weatherapplication.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class ItemspaceDco (
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position != RecyclerView.NO_POSITION) {
            // Add top spacing to all items except the first one
            outRect.top = if (position == 0) 0 else spacing
            outRect.bottom = spacing
        }
    }
}