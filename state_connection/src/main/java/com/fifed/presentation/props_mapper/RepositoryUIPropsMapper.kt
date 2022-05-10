package com.fifed.presentation.props_mapper

import com.fifed.presentation.ui_props.RepositoryUIProps
import com.fifed.state.AppState

interface RepositoryUIPropsMapper {
    fun map(state: AppState): RepositoryUIProps
}