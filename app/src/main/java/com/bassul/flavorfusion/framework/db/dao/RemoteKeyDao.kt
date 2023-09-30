package com.bassul.flavorfusion.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.bassul.core.data.DbConstants
import com.bassul.flavorfusion.framework.db.entity.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM ${DbConstants.REMOTE_KEYS_TABLE_NAME}")
    suspend fun remoteKey(): RemoteKey

    @Query("DELETE FROM ${DbConstants.REMOTE_KEYS_TABLE_NAME}")
    suspend fun clearAll()
}