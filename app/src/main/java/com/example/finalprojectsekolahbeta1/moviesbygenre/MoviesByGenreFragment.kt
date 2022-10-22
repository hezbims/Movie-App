package com.example.finalprojectsekolahbeta1.moviesbygenre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalprojectsekolahbeta1.MainActivity
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.databinding.FragmentMoviesByGenreBinding

class MoviesByGenreFragment : Fragment() {
    private lateinit var viewModel: MoviesByGenreViewModel
    private lateinit var binding : FragmentMoviesByGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this ,
            MoviesByGenreViewModelFactory(
                MoviesByGenreFragmentArgs.fromBundle(requireArguments()).genre,
                requireActivity().application
            )
        )[MoviesByGenreViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesByGenreBinding
            .inflate(layoutInflater , container , false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.moviesList.observe(viewLifecycleOwner){
            it?.let {
                binding.recyclerView.adapter = GridMoviePosterAdapter(
                    GridMoviePosterAdapter.GridMovieNavigateEventHandler(
                        viewModel::navigateToDetail
                    )
                ).apply { submitList(it) }

                binding.recyclerView.layoutManager =
                    GridLayoutManager(requireContext() , 2)
            }
        }

        viewModel.currentMovie.observe(viewLifecycleOwner){
            it?.let{ currentMovie ->
                val action = MoviesByGenreFragmentDirections
                    .actionMoviesByGenreFragmentToDetailFragment(currentMovie)
                (requireActivity()
                    .supportFragmentManager
                    .findFragmentById(R.id.nav_host) as NavHostFragment).apply {
                    navController.navigate(action)
                }

                viewModel.doneNavigatingToDetail()
            }
        }
        (requireActivity() as MainActivity).supportActionBar!!.title =
            getString(R.string.movie_by_genre_action_bar_title , viewModel.genre.name)

        return binding.root
    }

}