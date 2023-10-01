package com.bassul.flavorfusion.util

fun String.uppercaseFirstLetter() = this.replaceFirstChar { it.uppercaseChar() }