package com.fifed.presentation.connector

import com.fifed.presentation.ui_props.SearchUiProps
import kotlinx.coroutines.flow.Flow

interface SearchScreenConnector {
    val uiStateFlow: Flow<SearchUiProps>
    fun onInputChanged(text: String)
    fun onRetry()
    fun handleScroll(lastVisiblePosition: Int)
}