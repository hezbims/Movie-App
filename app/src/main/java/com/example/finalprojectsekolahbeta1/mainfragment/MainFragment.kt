package com.example.finalprojectsekolahbeta1.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalprojectsekolahbeta1.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(layoutInflater , container , false)

        viewModel.popularMovies.observe(viewLifecycleOwner){ it ->
            it?.let{
                binding.mostPopularRecyclerView.adapter = MainAdapter(EventHandler { currentMovie ->
                    viewModel.navigateToDetail(currentMovie)

                }).apply {
                    submitList(it)
                }
            }
        }
        viewModel.currentMovie.observe(viewLifecycleOwner){
            it?.let{ currentMovie ->
                val action = MainFragmentDirections
                    .actionMainFragmentToDetailFragment(currentMovie)
                findNavController().navigate(action)
                viewModel.doneNavigatingToDetail()
            }
        }

        return binding.root
    }
}