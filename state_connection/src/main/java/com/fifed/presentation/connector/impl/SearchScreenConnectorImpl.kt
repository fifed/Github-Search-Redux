package com.fifed.presentation.connector.impl

import com.fifed.presentation.connector.SearchScreenConnector
import com.fifed.presentation.props_mapper.SearchUIPropsMapper
import com.fifed.presentation.ui_props.SearchUiProps
import com.fifed.redux.action.search.SearchRepositoriesRetryAction
import com.fifed.redux.action.search.SearchScreenInputChangedAction
import com.fifed.redux.action.search.SearchScreenScrollAction
import com.fifed.redux.core.redux.Store
import com.fifed.tools.extension.flowOnAppThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SearchScreenConnectorImpl(
    private val store: Store,
    private val mapper: SearchUIPropsMapper
) : SearchScreenConnector {
    override val uiStateFlow: Flow<SearchUiProps> = store.stateFlow.map { state ->
        mapper.map(state)
    }.flowOnAppThread()

    override fun onInputChanged(text: String) {
        store.dispatch(SearchScreenInputChangedAction(text))
    }

    override fun onRetry() {
        store.dispatch(SearchRepositoriesRetryAction)
    }

    override fun handleScroll(lastVisiblePosition: Int) {
        store.dispatch(SearchScreenScrollAction(lastVisiblePosition))
    }
}