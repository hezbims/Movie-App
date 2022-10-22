package com.example.finalprojectsekolahbeta1

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

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

@BindingAdapter("setNoResultText")
fun setNoResultText(view : TextView , status: LoadingStatus){
    view.visibility =
        if (status == LoadingStatus.NORESULT) View.VISIBLE
        else View.GONE
}