package com.bassul.core.data.repository

import kotlinx.coroutines.flow.Flow

interface StorageLocalDataSource {

    val sorting: Flow<String>

    val sortingBy: Flow<String>
    suspend fun saveSorting(sorting: Pair<String, String>)
}