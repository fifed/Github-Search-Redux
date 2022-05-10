package com.fifed.data.repository

import com.fifed.model.RepoMapResult
import com.fifed.model.SearchMapResult

interface GitHubRepository {
    suspend fun searchRepositories(searchQuery: String, page: Int, pageSize: Int): Result<SearchMapResult>
    suspend fun getRepository(ownerName: String, repositoryName: String): Result<RepoMapResult>
}