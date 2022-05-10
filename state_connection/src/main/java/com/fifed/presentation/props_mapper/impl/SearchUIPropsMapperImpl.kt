package com.fifed.presentation.props_mapper.impl

import com.fifed.model.RepositoryItemModel
import com.fifed.model.RepositoryOwnerModel
import com.fifed.presentation.R
import com.fifed.presentation.props_mapper.SearchUIPropsMapper
import com.fifed.presentation.resource.ResourceManager
import com.fifed.presentation.ui_props.SearchItemUIProps
import com.fifed.presentation.ui_props.SearchUiProps
import com.fifed.state.AppState
import com.fifed.state.SearchState.Status

internal class SearchUIPropsMapperImpl(private val resourceManager: ResourceManager) : SearchUIPropsMapper {
    override fun map(state: AppState): SearchUiProps {
        return with(state.searchState) {
            SearchUiProps(
                searchText = input,
                latestQuery = latestQuery,
                showCentralError = status == Status.ERROR && itemsIds.isEmpty(),
                showCentralProgress = status == Status.LOADING && itemsIds.isEmpty(),
                showNoResults = status == Status.SUCCESS && itemsIds.isEmpty(),
                showDefaultPlaceHolder = status == Status.NONE,
                showBottomError = status == Status.ERROR && itemsIds.isNotEmpty(),
                showBottomProgress = status == Status.LOADING && itemsIds.isNotEmpty(),
                items = mapItems(itemsIds, state.entitiesState.repositories, state.entitiesState.owners)
            )
        }
    }

    private fun mapItems(
        itemsIds: List<Int>,
        repositories: Map<Int, RepositoryItemModel>,
        owners: Map<Int, RepositoryOwnerModel>
    ): List<SearchItemUIProps> {
        return itemsIds.map { id ->
            val repo = requireNotNull(repositories[id])
            SearchItemUIProps(
                id = repo.id,
                name = repo.name,
                type = getTypeText(repo.isPrivate),
                typeColor = getTypeColor(repo.isPrivate),
                ownerName = requireNotNull(owners[repo.ownerId]).login,
                ownerAvatar = requireNotNull(owners[repo.ownerId]).avatarUrl
            )
        }
    }

    private fun getTypeText(isPrivate: Boolean): String {
        return resourceManager.getString(if (isPrivate) R.string._private else R.string._public)
    }

    private fun getTypeColor(isPrivate: Boolean): Int {
        return resourceManager.getColor(if (isPrivate) R.color.teal_700 else R.color.purple_700)
    }
}