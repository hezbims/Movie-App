package com.example.finalprojectsekolahbeta1.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.databinding.FragmentDetailBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class DetailFragment : Fragment(){
    private lateinit var viewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this , DetailViewModelFactory(
            DetailFragmentArgs.fromBundle(requireArguments()).currentMovie,
            requireActivity().application
        ))[DetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.videos.observe(viewLifecycleOwner) {
            it?.let {
                binding.videoRecyclerView.adapter = DetailRecyclerViewAdapter().apply {
                    submitList(it)
                }
            }
        }

        binding.favoriteIcon.setOnClickListener {
            if (it.tag == getString(R.string.is_favorite_tag))
                viewModel.deleteFromFavorite()
            else
                viewModel.addToFavorite()
        }

        Picasso.get().load(getString(R.string.poster_url , viewModel.currentMovie.backdropPath))
            .into(binding.thumbnail , object : Callback{
                override fun onSuccess() {
                    binding.progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    binding.progressBar.visibility = View.GONE
                    binding.internetFail.visibility = View.VISIBLE
                }
            })

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }
}