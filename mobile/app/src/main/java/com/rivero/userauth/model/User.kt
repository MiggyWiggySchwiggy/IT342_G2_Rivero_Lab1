package com.rivero.userauth.model

data class User(
    val userID: Long? = null,
    val username: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val passwordHash: String? = null // Matches the JSON field expected by backend
)