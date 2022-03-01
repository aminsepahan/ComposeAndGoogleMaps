package com.amin.composeandmaps.shared.util

sealed class OperationState {

    class Idle : OperationState()
    class Loading : OperationState()
    class Success : OperationState()
    class Error(val message: String? = null, ) : OperationState()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error
}

fun idle() = OperationState.Idle()
fun loading() = OperationState.Loading()
fun success() = OperationState.Success()

fun errorState(
    message: String? = null,
) = OperationState.Error(message)