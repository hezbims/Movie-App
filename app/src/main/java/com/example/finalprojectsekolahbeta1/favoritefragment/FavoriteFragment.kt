package com.example.finalprojectsekolahbeta1.favoritefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalprojectsekolahbeta1.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this , FavoriteViewModelFactory(requireActivity().application))[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoriteBinding.inflate(
            inflater,
            container,
            false
        )
        viewModel.movies.observe(viewLifecycleOwner){
            it?.let {
                binding.recyclerView.adapter = FavoriteAdapter().apply {
                    submitList(it)
                }
            }
        }


        return binding.root
    }


}