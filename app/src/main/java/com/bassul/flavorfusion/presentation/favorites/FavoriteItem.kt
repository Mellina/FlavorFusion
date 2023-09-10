package com.bassul.flavorfusion.presentation.favorites

import com.bassul.flavorfusion.presentation.common.ListItem

data class FavoriteItem (
    val id: Long,
    val name: String,
    val imageUrl: String,
    override val key: Long = id
) : ListItem