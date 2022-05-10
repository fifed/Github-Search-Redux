package com.fifed.state

data class RepositoryState(
    val status: Status = Status.NONE,
    val repositoryId: Int? = null,
    val ownerId: Int? = null
) {
    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
        NONE
    }
}
