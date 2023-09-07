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

    override suspend fun saveSorting(sorting: String) {
        storageLocalDataSource.saveSorting(sorting)
    }
}