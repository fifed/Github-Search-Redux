package com.fifed.data.mapper.impl

import com.fifed.data.mapper.RepositoriesAPIMapper
import com.fifed.data.model.api_model.responses.LicenseApiModel
import com.fifed.data.model.api_model.responses.OwnerApiModel
import com.fifed.data.model.api_model.responses.RepositoryApiModel
import com.fifed.data.model.api_model.responses.SearchRepositoriesResponse
import com.fifed.model.*

internal class RepositoriesAPIMapperImpl : RepositoriesAPIMapper {
    override fun map(response: SearchRepositoriesResponse): SearchMapResult {
        val owners: MutableSet<RepositoryOwnerModel> = mutableSetOf()
        val repos = response.repositories.map { item ->
            owners.add(mapOwner(item.owner))
            mapItem(item)
        }
        return SearchMapResult(repos, owners)
    }

    override fun map(model: RepositoryApiModel): RepoMapResult {
        return RepoMapResult(mapItem(model), mapOwner(model.owner))
    }

    private fun mapItem(model: RepositoryApiModel): RepositoryItemModel {
        return with(model) {
            RepositoryItemModel(
                id,
                mapLicense(license),
                name,
                owner.id,
                private,
                language,
                htmlUrl,
                defaultBranch,
                forks,
                openIssues
            )
        }
    }

    private fun mapLicense(licence: LicenseApiModel?): RepositoryLicenseModel? {
        if (licence == null) return null
        return licence.run { RepositoryLicenseModel(key, name, nodeId, spdxId, url) }
    }

    private fun mapOwner(owner: OwnerApiModel): RepositoryOwnerModel {
        return owner.run {
            RepositoryOwnerModel(
                avatarUrl, eventsUrl, followersUrl, followingUrl, gistsUrl, gravatarId, htmlUrl, id, login, nodeId,
                organizationsUrl, receivedEventsUrl, reposUrl, siteAdmin, starredUrl, subscriptionsUrl, type, url
            )
        }
    }
}