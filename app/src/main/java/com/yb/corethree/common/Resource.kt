package com.yb.corethree.common

data class Resource<out T>(val status: ResourceStatus, val data: T?, val error: Throwable? = null) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceStatus.SUCCESS, data)
        }

        fun <T> error(data: T?, error: Throwable): Resource<T> {
            return Resource(ResourceStatus.ERROR, data, error)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(ResourceStatus.LOADING, data)
        }
    }

    val isLoading: Boolean
        get() = status == ResourceStatus.LOADING

    val isError: Boolean
        get() = status == ResourceStatus.ERROR

    val isSuccess: Boolean
        get() = status == ResourceStatus.SUCCESS
}

enum class ResourceStatus {
    SUCCESS,
    ERROR,
    LOADING
}