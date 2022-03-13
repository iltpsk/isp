package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Actor
import ru.androidschool.intensiv.databinding.ItemActorBinding
import ru.androidschool.intensiv.ui.extensions.loadImage

class ActorItem(private val content: Actor) : BindableItem<ItemActorBinding>() {
    override fun bind(binding: ItemActorBinding, position: Int) {
        with(binding) {
            firstname.text = content.firstName
            lastname.text = content.lastName
            photo.loadImage(content.photoUrl)
        }
    }

    override fun getLayout(): Int = R.layout.item_actor

    override fun initializeViewBinding(v: View): ItemActorBinding = ItemActorBinding.bind(v)
}