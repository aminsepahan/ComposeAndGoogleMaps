package com.amin.composeandmaps.shared.util

sealed class UIState<out T>(open val data: T? = null) {

    class Idle : UIState<Nothing>()
    class Loading<out T>(val progress: Int = 0, override val data: T? = null) : UIState<T>()
    class Success<out T>(override val data: T) : UIState<T>()
    class Error(
        val error: Throwable? = null,
        val message: String? = null,
        val messageId: Int? = null,
        val title: String? = null,
        val titleId: Int? = null

    ) : UIState<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error
}

fun <T> idle() = UIState.Idle()
fun <T> loading(data: T? = null) = UIState.Loading<T>(data = data)

fun <T> success(data: T) = UIState.Success(data)

fun errorState(
    error: Throwable? = null,
    message: String? = null,
    messageId: Int? = null,
    title: String? = null,
    titleId: Int? = null
) = UIState.Error(error, message, messageId, title, titleId)