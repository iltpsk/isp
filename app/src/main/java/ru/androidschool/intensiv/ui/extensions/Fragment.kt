package ru.androidschool.intensiv.ui.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.androidschool.intensiv.ui.delegate.ViewBindingFragmentDelegate

inline fun <reified T : ViewBinding> Fragment.viewBinding(noinline initializer: (View) -> T) =
    ViewBindingFragmentDelegate(this, initializer)