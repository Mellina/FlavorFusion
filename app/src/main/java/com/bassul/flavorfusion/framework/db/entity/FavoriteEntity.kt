package com.bassul.flavorfusion.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bassul.core.data.DbConstants.FAVORITES_COLUMN_INFO_ID
import com.bassul.core.data.DbConstants.FAVORITES_COLUMN_INFO_IMAGE_URL
import com.bassul.core.data.DbConstants.FAVORITES_COLUMN_INFO_NAME
import com.bassul.core.data.DbConstants.FAVORITES_TABLE_NAME
import com.bassul.core.domain.model.Recipe

@Entity (tableName = FAVORITES_TABLE_NAME)
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = FAVORITES_COLUMN_INFO_ID)
    val id: Long,
    @ColumnInfo(name = FAVORITES_COLUMN_INFO_NAME)
    val name: String,
    @ColumnInfo(name = FAVORITES_COLUMN_INFO_IMAGE_URL)
    val imageUrl: String
)

fun List<FavoriteEntity>.toRecipesModel() = map {
    Recipe(it.id, it.name, it.imageUrl)
}