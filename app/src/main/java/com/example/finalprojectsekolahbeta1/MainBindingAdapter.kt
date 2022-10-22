package com.example.finalprojectsekolahbeta1

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectsekolahbeta1.database.Movie
import com.example.finalprojectsekolahbeta1.detailfragment.NavigateEventHandler


@BindingAdapter("loadingImage")
fun setLoadingImage(view : AppCompatImageView , status: LoadingStatus?){
    status?.let {
        when (it) {
            LoadingStatus.LOADING -> view.setImageResource(R.drawable.ic_baseline_hourglass)
            LoadingStatus.FAILURE -> view.setImageResource(R.drawable.ic_baseline_no_internet)
            else -> view.visibility = View.GONE
        }
    }
}

@BindingAdapter(value = ["movies", "event"] , requireAll = true)
fun setLinearRecyclerViewData(view : RecyclerView , movies : List<Movie?>? , event : NavigateEventHandler?){

}

@BindingAdapter("isFavorite")
fun setIsFavorite(view : AppCompatImageView , drawableId : Int){
    view.setImageResource(drawableId)
    view.tag =
        if (drawableId == R.drawable.ic_baseline_favorite_red)
            view.context.getString(R.string.is_favorite_tag)
        else
            view.context.getString(R.string.not_favorite_tag)
}

@BindingAdapter("setNoResultText")
fun setNoResultText(view : TextView , status: LoadingStatus){
    view.visibility =
        if (status == LoadingStatus.NORESULT) View.VISIBLE
        else View.GONE
}