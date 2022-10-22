package com.example.finalprojectsekolahbeta1.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var viewModel : MainViewModel
    private val adapter: MainMoviePosterAdapter
        get() = MainMoviePosterAdapter(
            MainMoviePosterAdapter.MovieNavigateEventHandler(
                viewModel::navigateToDetail
            )
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireActivity().application)
        )[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(layoutInflater , container , false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.popularMovies.movies.observe(viewLifecycleOwner){
            it?.let{
                binding.mostPopularRecyclerView.adapter = adapter.apply { submitList(it) }
            }
        }

        viewModel.popularMovies.movies.observe(viewLifecycleOwner){
            it?.let{
                binding.topRatedRecyclerView.adapter = adapter.apply { submitList(it) }
            }
        }

        viewModel.upComingMovies.movies.observe(viewLifecycleOwner){
            it?.let{
                binding.upcomingRecyclerView.adapter = adapter.apply { submitList(it) }
            }
        }

        viewModel.currentMovie.observe(viewLifecycleOwner){
            it?.let{ currentMovie ->
                val action = MainFragmentDirections.navigateToDetail(currentMovie)
                (requireActivity()
                    .supportFragmentManager
                    .findFragmentById(R.id.nav_host) as NavHostFragment).apply {
                    navController.navigate(action)
                }

                viewModel.doneNavigatingToDetail()
            }
        }

        return binding.root
    }
}