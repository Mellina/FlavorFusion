package com.bassul.flavorfusion.framework.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bassul.core.data.DbConstants.RECIPE_TABLE_NAME
import com.bassul.core.domain.model.Recipe
import com.bassul.flavorfusion.framework.db.entity.RecipeEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("SELECT * FROM $RECIPE_TABLE_NAME")
    fun pagingSource(): PagingSource<Int, RecipeEntity>

    @Query("DELETE FROM $RECIPE_TABLE_NAME")
    suspend fun clearAll()
}