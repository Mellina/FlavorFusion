package com.bassul.flavorfusion.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bassul.core.data.DbConstants

@Entity(tableName = DbConstants.RECIPE_TABLE_NAME)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val autoId: Long = 0,
    @ColumnInfo(name = DbConstants.RECIPE_COLUMN_INFO_ID)
    val id: Long,
    @ColumnInfo(name = DbConstants.RECIPE_COLUMN_INFO_NAME)
    val name: String,
    @ColumnInfo(name = DbConstants.RECIPE_COLUMN_INFO_IMAGE_URL)
    val imageUrl: String
)