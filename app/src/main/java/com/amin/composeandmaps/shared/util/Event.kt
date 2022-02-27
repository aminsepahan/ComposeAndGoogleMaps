package com.amin.composeandmaps.shared.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData

@Composable
fun <T> SelfDestructEvent(liveData: LiveData<T>, onEvent: (argument: T) -> Unit) {
    val previousState = remember { mutableStateOf(false) }
    val state by liveData.observeAsState(null)
    if (state != null && !previousState.value) {
        previousState.value = true
        onEvent.invoke(state!!)
    }
}

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}