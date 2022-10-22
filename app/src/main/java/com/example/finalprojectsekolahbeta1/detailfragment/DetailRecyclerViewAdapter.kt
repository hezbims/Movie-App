package com.example.finalprojectsekolahbeta1.detailfragment

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.database.Video
import com.example.finalprojectsekolahbeta1.databinding.ViewHolderDetailBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class DetailRecyclerViewAdapter : ListAdapter<Video, DetailRecyclerViewAdapter.ViewHolder>(
    DiffCallback()
){

    class ViewHolder(private val binding : ViewHolderDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item : Video){
            binding.apply{
                videoSource.text = root.context
                    .getString(R.string.source_text , item.site)
                Picasso.get()
                    .load(Uri.parse(root.context.getString(R.string.video_thumbnail_url , item.key)))
                    .into(videoThumbnail , object : Callback{
                        override fun onSuccess() {
                            progressBar.visibility = View.GONE
                        }
                        override fun onError(e: Exception?) {
                            progressBar.visibility = View.GONE
                            internetFail.visibility = View.VISIBLE
                        }
                    })
                videoTitle.text = item.name
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ViewHolderDetailBinding
                .inflate(LayoutInflater.from(parent.context) , parent , false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class DiffCallback : DiffUtil.ItemCallback<Video>(){
    override fun areItemsTheSame(oldItem: Video, newItem: Video) =
        oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Video , newItem: Video) =
        oldItem == newItem
}