/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache, Version 2.0 (the "License");
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

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.jn.easyjson.gson.GsonJSONBuilder;
import com.jn.langx.util.reflect.Reflects;

import java.util.Date;

public class DateTypeAdapterFactory extends EasyjsonAbstractTypeAdapterFactory {

    public DateTypeAdapterFactory(GsonJSONBuilder jsonBuilder) {
        super(jsonBuilder);
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (Reflects.isSubClassOrEquals(Date.class, type.getRawType())) {
            DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
            dateTypeAdapter.setDateFormat(jsonBuilder.serializeUseDateFormat());
            dateTypeAdapter.setUsingToString(jsonBuilder.serializeDateUsingToString());
            dateTypeAdapter.setJSONBuilder(jsonBuilder);
            return (TypeAdapter<T>) dateTypeAdapter;
        }
        return null;
    }
}
