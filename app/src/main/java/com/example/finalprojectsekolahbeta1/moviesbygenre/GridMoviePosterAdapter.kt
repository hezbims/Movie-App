package com.example.finalprojectsekolahbeta1.moviesbygenre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.database.Movie
import com.example.finalprojectsekolahbeta1.databinding.ViewHolderMoviePosterGridBinding
import com.example.finalprojectsekolahbeta1.detailfragment.NavigateToDetailEventHandler
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class GridMoviePosterAdapter(
    private val eventHandler : NavigateToDetailEventHandler,
) : ListAdapter<Movie, GridMoviePosterAdapter.MoviePosterViewHolder>(
    Movie.Companion.MovieDiffUtil()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MoviePosterViewHolder(
            ViewHolderMoviePosterGridBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ) , eventHandler
        )

    override fun onBindViewHolder(holder: MoviePosterViewHolder, position: Int) =
        holder.bind(getItem(position))
    class MoviePosterViewHolder(private val binding : ViewHolderMoviePosterGridBinding,
                                private val eventHandler: NavigateToDetailEventHandler,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : Movie){
            binding.movieCover.apply{
                shapeAppearanceModel = shapeAppearanceModel.toBuilder().setAllCornerSizes(
                    binding.root.context.resources.getDimension(R.dimen.rounded_corner)
                ).build()

                Picasso.get()
                    .load(binding.root.context.getString(R.string.image_path, item.posterPath))
                    .into(this , object : Callback{
                        override fun onSuccess() {
                            binding.progressBar.visibility = View.GONE
                        }

                        override fun onError(e: Exception?) {
                            setImageResource(R.drawable.ic_baseline_no_internet)
                        }
                    })

                setOnClickListener {
                    eventHandler.navigate(item)
                }
            }
        }
    }
}