package com.example.photorating.models

data class Photo(
    val id: String,
    val url: String,
    val description: String?,
    val likes: Int,
    val userId: String,
    val timestamp: Long
)