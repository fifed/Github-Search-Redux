package com.fifed.presentation.connector.impl

import com.fifed.presentation.connector.RepositoryScreenConnector
import com.fifed.presentation.props_mapper.RepositoryUIPropsMapper
import com.fifed.presentation.ui_props.RepositoryUIProps
import com.fifed.redux.action.repository.LoadRepositoryAction
import com.fifed.redux.core.redux.Store
import com.fifed.tools.extension.flowOnAppThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RepositoryScreenConnectorImpl(
    private val store: Store,
    private val mapper: RepositoryUIPropsMapper
) :
    RepositoryScreenConnector {
    override val uiFlow: Flow<RepositoryUIProps> = store.stateFlow.map { state ->
        mapper.map(state)
    }.flowOnAppThread()

    override fun load(id: Int, owner: String, repository: String) {
        store.dispatch(LoadRepositoryAction(id, owner, repository))
    }
}