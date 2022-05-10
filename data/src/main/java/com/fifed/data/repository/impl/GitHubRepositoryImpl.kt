package com.fifed.data.repository.impl

import com.fifed.data.mapper.RepositoriesAPIMapper
import com.fifed.data.network.RepositoriesApi
import com.fifed.data.repository.GitHubRepository
import com.fifed.model.RepoMapResult
import com.fifed.model.SearchMapResult
import com.fifed.tools.function.runCatchingWithContext

internal class GitHubRepositoryImpl(
    private val repositoriesApi: RepositoriesApi,
    private val apiMapper: RepositoriesAPIMapper,
) : GitHubRepository {

    override suspend fun searchRepositories(searchQuery: String, page: Int, pageSize: Int): Result<SearchMapResult> {
        return runCatchingWithContext {
            repositoriesApi.searchRepositories(searchQuery, page, pageSize).let { response ->
                apiMapper.map(response)
            }
        }
    }

    override suspend fun getRepository(ownerName: String, repositoryName: String): Result<RepoMapResult> {
        return runCatchingWithContext {
            apiMapper.map(repositoriesApi.getRepository(ownerName, repositoryName))
        }
    }
}