package com.example.finalprojectsekolahbeta1.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.databinding.DetailFragmentBinding
import com.squareup.picasso.Picasso

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
        val binding = DetailFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        viewModel.videos.observe(viewLifecycleOwner) {
            it?.let {
                binding.videoRecyclerView.adapter = DetailRecyclerViewAdapter().apply {
                    submitList(it)
                }
            }
        }

        Picasso.get().load(getString(R.string.image_path , viewModel.currentMovie.backdropPath))
            .into(binding.thumbnail)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }
}