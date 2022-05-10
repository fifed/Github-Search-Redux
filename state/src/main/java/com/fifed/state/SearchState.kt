package com.fifed.state

data class SearchState(
    val input: String = "",
    val itemsIds: List<Int> = emptyList(),
    val loadedPages: Int = 0,
    val latestQuery: String = "",
    val status: Status = Status.NONE
) {
    enum class Status {
        LOADING,
        ERROR,
        SUCCESS,
        NONE
    }
}
