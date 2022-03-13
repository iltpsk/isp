package ru.androidschool.intensiv.ui.delegate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingFragmentDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    private val initializer: (View) -> T
) : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
    private var _value: T? = null

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewOwner ->
            viewOwner.lifecycle.addObserver(this)
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = _value
        if (binding != null) return binding
        val lifecycle = thisRef.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("It is impossible to attempt to get bindings when Fragment views are destroyed")
        }

        return initializer.invoke(thisRef.requireView()).also { this._value = it }
    }

    override fun onCreate(owner: LifecycleOwner) {
        if (_value == null) _value = initializer.invoke(fragment.requireView())
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _value = null
    }
}
