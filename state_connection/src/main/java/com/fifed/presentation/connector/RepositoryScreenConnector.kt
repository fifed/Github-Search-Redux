package com.fifed.presentation.connector

import com.fifed.presentation.ui_props.RepositoryUIProps
import kotlinx.coroutines.flow.Flow

interface RepositoryScreenConnector {
    val uiFlow: Flow<RepositoryUIProps>
    fun load(id: Int, owner: String, repository: String)
}