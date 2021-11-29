package com.test.iprobonusapi.data

data class PostAccessRequestBody(
    val accessToken: String,
    val idClient: String,
    val latitude: Int,
    val longitude: Int,
    val paramName: String,
    val paramValue: String,
    val sourceQuery: Int
)