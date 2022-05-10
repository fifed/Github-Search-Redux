package com.fifed.model

data class RepositoryItemModel(
    val id: Int,
    val license: RepositoryLicenseModel?,
    val name: String,
    val ownerId: Int,
    val isPrivate: Boolean,
    val language: String?,
    val url: String,
    val defaultBranch: String,
    val forks: Int,
    val issues: Int
)