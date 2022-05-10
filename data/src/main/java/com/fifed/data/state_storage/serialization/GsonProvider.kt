package com.fifed.data.state_storage.serialization

import com.fifed.domain.state_storage.serialization.adapter.GsonMapAdapter
import com.fifed.model.RepositoryItemModel
import com.fifed.model.RepositoryOwnerModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

internal object GsonProvider {
    fun provide(): Gson {
        val mapAdapter = GsonMapAdapter()
        return GsonBuilder()
            .registerTypeAdapter(getMapType<Int, RepositoryItemModel>(), mapAdapter)
            .registerTypeAdapter(getMapType<Int, RepositoryOwnerModel>(), mapAdapter)
            .create().apply {
                mapAdapter.setGson(this)
            }
    }

    private fun <K, V> getMapType() = object : TypeToken<Map<K, V>>() {}.type
}