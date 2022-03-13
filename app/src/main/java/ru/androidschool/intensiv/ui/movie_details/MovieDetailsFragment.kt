package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.ui.extensions.loadImage
import ru.androidschool.intensiv.ui.extensions.viewBinding
import ru.androidschool.intensiv.ui.feed.FeedFragment
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private val viewBinding: MovieDetailsFragmentBinding by viewBinding(MovieDetailsFragmentBinding::bind)
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(FeedFragment.KEY_TITLE) ?: ""
        Timber.d("retrieved title = $title")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MockRepository.getMovieByTitle(title).also { movie ->
            with(viewBinding) {
                moviePoster.loadImage("https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg")
                title.text = movie.title
                description.text = movie.description
                movieRating.rating = movie.rating
                genre.text = movie.genre
                studio.text = movie.studio
                year.text = movie.year.toString()
                actorsRecyclerView.adapter =
                    adapter.apply { addAll(movie.actors.map { ActorItem(it) }) }
                backButton.setOnClickListener { openFeed() }
            }
        }
    }

    private fun openFeed() {
        findNavController().popBackStack()
    }
}
