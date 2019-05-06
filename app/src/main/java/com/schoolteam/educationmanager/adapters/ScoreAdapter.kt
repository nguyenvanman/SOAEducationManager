package com.schoolteam.educationmanager.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.schoolteam.educationmanager.R
import com.schoolteam.educationmanager.adapters.holders.ScoreViewHolder

class ScoreAdapter(val list: List<Float>) : RecyclerView.Adapter<ScoreViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ScoreViewHolder {
        return ScoreViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.score_item, p0, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: ScoreViewHolder, p1: Int) {
        p0.mark = list[p1]
    }

}