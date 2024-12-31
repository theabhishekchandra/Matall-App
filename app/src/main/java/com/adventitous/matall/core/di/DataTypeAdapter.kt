package com.adventitous.matall.core.di

import com.google.gson.*
import java.lang.reflect.Type

class DataTypeAdapter : JsonDeserializer<Any> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Any {
        return if (json!!.isJsonObject) {
            context!!.deserialize(json, JsonObject::class.java)
        } else {
            json.asString
        }

    }
}
