package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.ui.extensions.viewBinding

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {
    private val viewBinding: TvShowsFragmentBinding by viewBinding(TvShowsFragmentBinding::bind)

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Используя Мок-репозиторий получаем фэйковый список фильмов
        val tvShowsList = MockRepository
            .getMovies()
            .map { TvShowItem(it) }

        viewBinding.tvShowsRecyclerView.adapter = adapter.apply { addAll(tvShowsList) }
    }
}