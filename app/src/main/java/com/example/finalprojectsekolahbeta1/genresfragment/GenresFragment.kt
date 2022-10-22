package com.example.finalprojectsekolahbeta1.genresfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.databinding.FragmentGenresBinding

class GenresFragment : Fragment() {
    private lateinit var viewModel : GenresViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this ,
            GenresViewModelFactory(requireActivity().application)
        )[GenresViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGenresBinding.inflate(layoutInflater , container , false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.genreResult.observe(viewLifecycleOwner){
            it?.let{
                binding.recyclerView.adapter = GenresAdapter(
                    GenresAdapter.GenreNavigateEventHandler(
                        viewModel::navigateToMoviesByGenre
                    )
                ).apply {
                    submitList(it)
                }
            }
        }

        viewModel.currentGenre.observe(viewLifecycleOwner){
            it?.let{
                val action = GenresFragmentDirections
                    .actionGenresFragmentToMoviesByGenreFragment(it)
                (requireActivity()
                    .supportFragmentManager
                    .findFragmentById(R.id.nav_host) as NavHostFragment).apply {
                        navController.navigate(action)
                }
                viewModel.doneNavigateToMoviesByGenre()
            }
        }

        return binding.root
    }
}