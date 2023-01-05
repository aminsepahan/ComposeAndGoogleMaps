package com.amin.composeandmaps.shared.util

sealed class Result<T> {

    data class Success<T>(val value: T) : Result<T>()

    data class Failure<T>(val throwable: Throwable) : Result<T>()

    val isSuccess : Boolean get() {
        return this is Success
    }

}