package com.omnicluster.associatecertification.util.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
/*
* Based on https://gist.github.com/frel/5f3f928c27f4106ffd420a3d99c8037c
*
* Refactored to use the preferred `DefaultLifecycleObserver` which does not rely on the annotation processor.
* See https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle#init
*
* Usage:
* `private val binding by viewLifecycle(TheViewBinding::bind)`
*/

/**
 * An extension to bind and unbind a value based on the view lifecycle of a Fragment.
 * The binding will be unbound in onDestroyView.
 *
 * @throws IllegalStateException If the getter is invoked before the binding is set,
 *                               or after onDestroyView an exception is thrown.
 */
fun <T> Fragment.viewLifecycle(viewBindingFactory: (View) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        private var binding: T? = null

        init {
            // Observe the view lifecycle of the Fragment.
            // The view lifecycle owner is null before onCreateView and after onDestroyView.
            // The observer is automatically removed after the onDestroy event.
            this@viewLifecycle
                .viewLifecycleOwnerLiveData
                .observe(this@viewLifecycle, Observer { owner: LifecycleOwner? ->
                    owner?.lifecycle?.addObserver(this)
                })
        }

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): T {
            val binding = binding
            if (binding != null) {
                return binding
            }
            val lifecycle = viewLifecycleOwner.lifecycle
            if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                throw IllegalStateException("Called before onCreateView or after onDestroyView.")
            }

            return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
        }
    }