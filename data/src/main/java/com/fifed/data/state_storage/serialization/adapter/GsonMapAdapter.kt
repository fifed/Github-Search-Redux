package com.fifed.domain.state_storage.serialization.adapter

import com.google.gson.*
import java.lang.reflect.Type

class GsonMapAdapter : JsonDeserializer<Map<*,  *>>, JsonSerializer<Map<*, *>> {

    private lateinit var gson: Gson

    fun setGson(gson: Gson) {
        this.gson = gson
    }

    @Throws(JsonParseException::class)
    override fun deserialize(jsonElement: JsonElement, type: Type, context: JsonDeserializationContext): Map<*, *> {
        return gson.fromJson(jsonElement, type)
    }

    override fun serialize(src: Map<*, *>, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return gson.toJsonTree(src)
    }

}