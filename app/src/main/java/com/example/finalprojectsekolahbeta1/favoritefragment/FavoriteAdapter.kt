package com.example.finalprojectsekolahbeta1.favoritefragment

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.database.MovieFavorite
import com.example.finalprojectsekolahbeta1.databinding.ViewHolderFavoriteBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class FavoriteAdapter : ListAdapter<MovieFavorite , FavoriteAdapter.ViewHolder>(MovieFavorite.Companion.MovieFavoriteDiffUtil()){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ViewHolderFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    class ViewHolder(
        private val binding : ViewHolderFavoriteBinding,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : MovieFavorite){
            binding.ratingText.text = binding.root.context
                .getString(R.string.rating_format , item.voteAverage)
            binding.trendingText.text = binding.root.context
                .getString(R.string.rating_format , item.popularity)
            binding.releaseDateText.text = binding.root.context
                .getString(R.string.release_date_format , item.releaseDate)
            binding.title.text = item.title
            Picasso.get().load(
                Uri.parse(binding.root.context.getString(R.string.poster_url , item.posterPath))
            )
                .into(binding.moviePoster , object : Callback{
                    override fun onSuccess() {
                        binding.progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        binding.progressBar.visibility = View.GONE
                        binding.internetFail.visibility = View.VISIBLE
                    }
                })
            binding.outerLayout.setOnClickListener {

            }
        }
    }
}