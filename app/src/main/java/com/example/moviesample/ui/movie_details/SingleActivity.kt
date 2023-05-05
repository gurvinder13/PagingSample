package com.example.moviesample.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviesample.data.api.POSTER_BASE_URL
import com.example.moviesample.data.api.TheMovieDBClient
import com.example.moviesample.data.api.TheMovieDBInterface
import com.example.moviesample.data.repository.NetworkState
import com.example.moviesample.data.model.MovieDetails
import com.example.moviesample.databinding.ActivitySingleBinding
import java.text.NumberFormat
import java.util.*

class SingleActivity : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    private lateinit var binding: ActivitySingleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId: Int = intent.getIntExtra("id", 1)
        println("id $movieId")

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this) {
            bindUI(it)
        }

        viewModel.networkState.observe(this) {
            binding.progressBar.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        }

    }

    private fun bindUI(it: MovieDetails?) {
        if (it != null) {
            binding.movieTitle.text = it.title
            binding.movieTagline.text = it.tagline
            binding.movieReleaseDate.text = it.releaseDate
            binding.movieRating.text = it.rating.toString() +"/ 10"
            binding.movieRuntime.text = it.runtime.toString() + " minutes"
            binding.movieOverview.text = it.overview

            val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
            binding.movieBudget.text = formatCurrency.format(it.budget)
            binding.movieRevenue.text = formatCurrency.format(it.revenue)

            val moviePosterURL = POSTER_BASE_URL + it.posterPath
            Glide.with(this).load(moviePosterURL).into(binding.ivMoviePoster)
        }

    }

    private fun getViewModel(movieId:Int) : SingleMovieViewModel{
        return ViewModelProvider(this, object :ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository,movieId)as T
            }

        })[SingleMovieViewModel::class.java]
    }

}
