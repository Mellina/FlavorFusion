package com.bassul.flavorfusion.presentation.extensions

import com.bassul.core.usecase.base.ResultStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

suspend fun <T> Flow<ResultStatus<T>>.watchStatus(
    loading: () -> Unit = {},
    success: (data: T) -> Unit,
    error: (throwable: Throwable) -> Unit
) {
    collect { status ->
        when (status) {
            ResultStatus.Loading -> loading.invoke()
            is ResultStatus.Success -> success.invoke(status.data)
            is ResultStatus.Error -> error.invoke(status.throwable)
        }

    }
}