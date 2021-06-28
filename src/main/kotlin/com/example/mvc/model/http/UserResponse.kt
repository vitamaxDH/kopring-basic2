package com.example.mvc.model.http

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class UserResponse (
    var result: Result? = null,
    var description: String? = null,
    var user: List<UserRequest>? = null
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Result (
    var resultCode: String? = null,
    var resultMessage: String? = null
)