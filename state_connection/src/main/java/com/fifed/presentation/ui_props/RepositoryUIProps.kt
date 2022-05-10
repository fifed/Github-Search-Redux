package com.fifed.presentation.ui_props

data class RepositoryUIProps(
    val showError: Boolean = false,
    val showProgress: Boolean = false,
    val showContent: Boolean = false,
    val name: String = "",
    val type: String = "",
    val ownerName: String = "",
    val ownerAvatar: String = "",
    val showLanguage: Boolean = false,
    val language: String = "",
    val showLicence: Boolean = false,
    val licenceName: String = "",
    val url: String = "",
    val defaultBranch: String = "",
    val forks: String = "",
    val issues: String = ""
)
