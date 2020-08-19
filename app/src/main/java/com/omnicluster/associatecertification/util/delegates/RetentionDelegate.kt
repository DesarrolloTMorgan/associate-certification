package com.omnicluster.associatecertification.util.delegates

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KProperty

/**
 * This class handles retention of any object using the ViewModel framework. An
 * example of this method being used can be found in the Loader Manager framework
 * in the Android support library.
 */
class RetentionDelegate<T>(
    private val viewModelStoreOwner: ViewModelStoreOwner,
    private val initializer: (() -> T)? = null
) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val (uniqueId, viewModel) = storeInformationFor(property)

        // If the value is in the ViewModel, return it
        @Suppress("UNCHECKED_CAST")
        if (viewModel.values.containsKey(uniqueId)) return viewModel.values[uniqueId] as T

        // If the viewModel doesn't hold the value and the initialiser is null, we're in an illegal state
        initializer
            ?: throw IllegalStateException("Attempting to invoke getValue when no value is present")

        val value = initializer.invoke()

        // Add the value to the ViewModel
        viewModel.values[uniqueId] = value as Any

        return value
    }

    /**
     * Convenience getter function for viewModel
     */
    private fun storeInformationFor(property: KProperty<*>): Pair<String, RetentionViewModel> {
        // Get ViewModel
        val viewModel = ViewModelProvider(viewModelStoreOwner)[RetentionViewModel::class.java]

        // Use the property as a unique ID for the viewModel value map
        val uniqueId = property.name

        return uniqueId to viewModel
    }
}

/**
 * ViewModel class for holding the values to be retained in the viewModelStore
 */
class RetentionViewModel : ViewModel() {
    val values: MutableMap<String, Any> = hashMapOf()
}

/**
 * Extension properties for ease of use
 */
fun <T> Fragment.retained(valInitializer: (() -> T)? = null) =
    RetentionDelegate(this, valInitializer)

fun <T> AppCompatActivity.retained(valInitializer: (() -> T)? = null) =
    RetentionDelegate(this, valInitializer)