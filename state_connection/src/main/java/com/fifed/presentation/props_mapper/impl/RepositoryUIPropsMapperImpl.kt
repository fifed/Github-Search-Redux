package com.fifed.presentation.props_mapper.impl

import com.fifed.presentation.R
import com.fifed.presentation.props_mapper.RepositoryUIPropsMapper
import com.fifed.presentation.resource.ResourceManager
import com.fifed.presentation.ui_props.RepositoryUIProps
import com.fifed.state.AppState
import com.fifed.state.RepositoryState

internal class RepositoryUIPropsMapperImpl(
    private val resourceManager: ResourceManager
) : RepositoryUIPropsMapper {
    override fun map(state: AppState): RepositoryUIProps {
        return with(state.repositoryState) {
            val repository = state.entitiesState.repositories[repositoryId]
            val owner = state.entitiesState.owners[ownerId]
            RepositoryUIProps(
                status == RepositoryState.Status.ERROR,
                status == RepositoryState.Status.LOADING,
                status == RepositoryState.Status.SUCCESS,
                name = repository?.name.orEmpty(),
                type = getTypeText(repository?.isPrivate ?: false),
                ownerName = owner?.login.orEmpty(),
                ownerAvatar = owner?.avatarUrl.orEmpty(),
                repository?.language != null,
                repository?.language.orEmpty(),
                repository?.license != null,
                repository?.license?.name.orEmpty(),
                repository?.url.orEmpty(),
                repository?.defaultBranch.orEmpty(),
                repository?.forks?.toString().orEmpty(),
                repository?.issues?.toString().orEmpty()
            )
        }
    }

    private fun getTypeText(isPrivate: Boolean): String {
        return resourceManager.getString(if (isPrivate) R.string._private else R.string._public)
    }

}