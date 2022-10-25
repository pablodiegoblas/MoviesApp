package com.example.mymoviesapp.domain.models

data class GuestSession(
    val success: Boolean,
    val guestSessionId: String?,
    val expiresAt: String?
)

data class RatingResponse(
    val success: Boolean,
    val statusMessage: String?
)