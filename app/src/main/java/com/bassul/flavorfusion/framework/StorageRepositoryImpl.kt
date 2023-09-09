package com.bassul.flavorfusion.framework

import com.bassul.core.data.repository.StorageLocalDataSource
import com.bassul.core.data.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storageLocalDataSource: StorageLocalDataSource
) : StorageRepository {
    override val sorting: Flow<String>
        get() = storageLocalDataSource.sorting
    override val sortingBy: Flow<String>
        get() = storageLocalDataSource.sortingBy

    override suspend fun saveSorting(sorting: Pair<String, String>) {
        storageLocalDataSource.saveSorting(sorting)
    }
}