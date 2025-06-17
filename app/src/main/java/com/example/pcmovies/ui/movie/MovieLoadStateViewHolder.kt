package com.example.pcmovies.ui.movie

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.pcmovies.R

class MovieLoadStateViewHolder(
    itemView: View,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
    private val btnRetry: Button = itemView.findViewById(R.id.btn_retry)

    init {
        btnRetry.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        btnRetry.isVisible = loadState is LoadState.Error
    }
}
