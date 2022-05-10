package com.fifed.presentation.props_mapper

import com.fifed.presentation.ui_props.SearchUiProps
import com.fifed.state.AppState

interface SearchUIPropsMapper {
    fun map(state: AppState): SearchUiProps
}