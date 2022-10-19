package com.example.finalprojectsekolahbeta1.mainfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.database.Movie
import com.example.finalprojectsekolahbeta1.databinding.FragmentMainViewHolderBinding
import com.squareup.picasso.Picasso

class MainAdapter(private val eventHandler : EventHandler) : ListAdapter<Movie , MainAdapter.ViewHolder>(MainDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            FragmentMainViewHolderBinding
                .inflate(LayoutInflater.from(parent.context) , parent , false),
            eventHandler
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder(private val binding : FragmentMainViewHolderBinding ,
                     private val eventHandler: EventHandler)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Movie){
            binding.movieCover.apply{
                shapeAppearanceModel = shapeAppearanceModel.toBuilder().setAllCornerSizes(
                    binding.root.context.resources.getDimension(R.dimen.rounded_corner)
                ).build()

                Picasso.get()
                    .load(binding.root.context.getString(R.string.image_path , item.posterPath))
                    .into(this)

                setOnClickListener {
                    eventHandler.navigateToDetail(item)
                }
            }
        }
    }
}

class EventHandler(val navigateToDetail : (movie : Movie) -> Unit)

class MainDiffUtil : DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem == newItem
}