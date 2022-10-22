package com.example.finalprojectsekolahbeta1.genresfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectsekolahbeta1.database.Genre
import com.example.finalprojectsekolahbeta1.databinding.ViewHolderGenresBinding

class GenresAdapter(private val eventHandler : GenreNavigateEventHandler) : ListAdapter<Genre , GenresAdapter.GenreViewHolder>(
    Genre.Companion.GenreDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenreViewHolder(
            ViewHolderGenresBinding.inflate(
                LayoutInflater.from(parent.context) , parent , false
            ),
            eventHandler
        )

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) =
        holder.bind(getItem(position))

    class GenreViewHolder(
        private val binding : ViewHolderGenresBinding,
        private val eventHandler: GenreNavigateEventHandler
    )
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Genre){
            binding.genreTextView.apply {
                text = item.name
                setOnClickListener {
                    eventHandler.navigate(item)
                }
            }
        }
    }

    class GenreNavigateEventHandler(val navigate : (genre : Genre?) -> Unit)
}