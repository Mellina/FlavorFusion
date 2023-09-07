package com.bassul.flavorfusion.framework.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bassul.core.data.repository.StorageConstants
import com.bassul.core.data.repository.StorageLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataSourcePreferencesDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : StorageLocalDataSource {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = StorageConstants.DATA_STORE_NAME
    )

    private val sortingKey = stringPreferencesKey(StorageConstants.SORT_ORDER_BY_KEY)
    override val sorting: Flow<String>
        get() = context.dataStore.data.map { store ->
            store[sortingKey] ?: ""
        }

    override suspend fun saveSorting(sorting: String) {
        context.dataStore.edit { store ->
            store[sortingKey] = sorting
        }
    }
}