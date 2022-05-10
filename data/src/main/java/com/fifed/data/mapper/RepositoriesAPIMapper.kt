package com.fifed.data.mapper

import com.fifed.data.model.api_model.responses.RepositoryApiModel
import com.fifed.data.model.api_model.responses.SearchRepositoriesResponse
import com.fifed.model.RepoMapResult
import com.fifed.model.SearchMapResult

internal interface RepositoriesAPIMapper {
    fun map(response: SearchRepositoriesResponse): SearchMapResult
    fun map(model: RepositoryApiModel): RepoMapResult

}