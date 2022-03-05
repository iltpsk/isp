package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.databinding.ItemTvShowBinding

class TvShowItem(private val content: Movie) : BindableItem<ItemTvShowBinding>() {
    override fun bind(view: ItemTvShowBinding, position: Int) {
        view.description.text = content.title
        view.movieRating.rating = content.rating

        // TODO Получать из модели
        Picasso.get()
            .load("https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg")
            .into(view.imagePreview)
    }

    override fun getLayout(): Int = R.layout.item_tv_show

    override fun initializeViewBinding(v: View): ItemTvShowBinding = ItemTvShowBinding.bind(v)
}