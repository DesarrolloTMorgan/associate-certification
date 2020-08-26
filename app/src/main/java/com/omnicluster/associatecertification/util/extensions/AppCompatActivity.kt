package com.omnicluster.associatecertification.util.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * Delegate to obtain an String given the StringRes Int id.
 * */
class StringsDelegates(
    @StringRes private val stringResourceId: Int
) : ReadOnlyProperty<AppCompatActivity, String> {
    private var resolvedString: String? = null

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): String =
        resolvedString ?: thisRef.getString(stringResourceId).also { resolvedString = it }

}

/**
 * Making an idiomatic call on AppCompatActivity.
 * */
fun AppCompatActivity.stringIds(@StringRes stringResourceId: Int) =
    StringsDelegates(stringResourceId)

fun AppCompatActivity.hideKeyboard() {
    val view: View? = currentFocus
    view?.let {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}