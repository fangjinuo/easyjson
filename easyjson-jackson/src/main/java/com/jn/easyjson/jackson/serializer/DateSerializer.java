/*
 * Copyright 2019 the original author or authors.
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

package com.jn.easyjson.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jn.easyjson.core.codec.dialect.PropertyCodecConfiguration;
import com.jn.easyjson.jackson.JacksonConstants;
import com.jn.easyjson.jackson.Jacksons;
import com.jn.langx.annotation.NonNull;
import com.jn.langx.util.Dates;
import com.jn.langx.util.Strings;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateSerializer extends JsonSerializer {
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider ctx) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        Date date = null;
        if (value instanceof Date) {
            date = (Date) value;
        } else if (value instanceof Calendar) {
            date = ((Calendar) value).getTime();
        }
        serializeDate(date, gen, ctx);
    }

    private void serializeDate(@NonNull Date value, JsonGenerator gen, SerializerProvider ctx)  throws IOException {
        DateFormat df = null;
        String pattern = null;

        if (Jacksons.getBooleanAttr(ctx, JacksonConstants.ENABLE_CUSTOM_CONFIGURATION)) {
            PropertyCodecConfiguration propertyCodecConfiguration = Jacksons.getPropertyCodecConfiguration(gen);
            if (propertyCodecConfiguration != null) {
                df = propertyCodecConfiguration.getDateFormat();
                pattern = propertyCodecConfiguration.getDatePattern();
                if (df == null && Strings.isNotBlank(pattern)) {
                    df = Dates.getSimpleDateFormat(pattern);
                }
            }
        }
        if (df == null) {
            df = Jacksons.getDateFormatAttr(ctx, JacksonConstants.SERIALIZE_DATE_USING_DATE_FORMAT_ATTR_KEY);
        }
        boolean usingToString = Jacksons.getBooleanAttr(ctx, JacksonConstants.SERIALIZE_DATE_USING_TO_STRING_ATTR_KEY);
        if (df == null && Strings.isNotBlank(pattern)) {
            df = Dates.getSimpleDateFormat(pattern);
        }

        if (df != null) {
            gen.writeString(df.format(value));
            return;
        }
        if (usingToString) {
            gen.writeString(value.toString());
        }
        gen.writeNumber(value.getTime());
    }

}
