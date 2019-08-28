/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the LGPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at  http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jn.easyjson.gson.typeadapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * priority: longUsingString > usingString > number
 */
public class NumberTypeAdapter implements JsonSerializer<Number>, JsonDeserializer<Number> {
    private boolean longUsingString;
    private boolean usingString;

    public void setLongUsingString(boolean longUsingString) {
        this.longUsingString = longUsingString;
    }

    public void setUsingString(boolean usingString) {
        this.usingString = usingString;
    }

    @Override
    public Number deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject() || json.isJsonArray() || json.isJsonNull()) {
            return null;
        }
        JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
        if (jsonPrimitive.isString()) {
            if (typeOfT == long.class || typeOfT == Long.class) {
                return Long.parseLong(jsonPrimitive.getAsString());
            }
            if (typeOfT == double.class || typeOfT == Double.class) {
                return Double.parseDouble(jsonPrimitive.getAsString());
            }
            if (typeOfT == int.class || typeOfT == Integer.class) {
                return Integer.parseInt(jsonPrimitive.getAsString());
            }
            if (typeOfT == float.class || typeOfT == Float.class) {
                return Float.parseFloat(jsonPrimitive.getAsString());
            }
            if (typeOfT == short.class || typeOfT == Short.class) {
                return Short.parseShort(jsonPrimitive.getAsString());
            }
            if (typeOfT == byte.class || typeOfT == Byte.class) {
                return Byte.parseByte(jsonPrimitive.getAsString());
            }
            if (typeOfT == BigDecimal.class) {
                return new BigDecimal(jsonPrimitive.getAsString());
            }
            if (typeOfT == BigInteger.class) {
                return new BigInteger(jsonPrimitive.getAsString());
            }
        }
        if (jsonPrimitive.isNumber()) {
            return jsonPrimitive.getAsNumber();
        }
        return 0;
    }

    @Override
    public JsonElement serialize(Number src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        if (longUsingString) {
            if (Long.class == typeOfSrc || long.class == typeOfSrc) {
                return new JsonPrimitive(src.toString());
            } else {
                return new JsonPrimitive(src);
            }
        }
        if (usingString) {
            return new JsonPrimitive(src.toString());
        }
        return new JsonPrimitive(src);
    }
}