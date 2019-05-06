package com.schoolteam.educationmanager.adapters.holders

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.score_item.*

class ScoreViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {
    var mark: Float = 0f
        set(value) {
            field = value
            display(value)
        }

    private fun display(value: Float) {
        tvScore.text = if (value < 0) "" else value.toString()
        tvScore.setBackgroundColor(Color.argb(12, 50, 19, 68))
        tvScore.setTextColor(Color.BLACK)
    }
}