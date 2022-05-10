package com.fifed.presentation.ui_props

data class SearchUiProps(
    val searchText: String = "",
    val latestQuery: String = "",
    val showCentralProgress: Boolean = false,
    val showCentralError: Boolean = false,
    val showNoResults: Boolean = false,
    val showDefaultPlaceHolder: Boolean = true,
    val showBottomProgress: Boolean = false,
    val showBottomError: Boolean = false,
    val items: List<SearchItemUIProps> = emptyList()
)
