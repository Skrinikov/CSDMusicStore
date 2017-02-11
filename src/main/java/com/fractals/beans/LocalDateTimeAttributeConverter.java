/*
 * The courtesy of https://github.com/ancoron/pg-inet-maven/wiki/Support-custom-data-types-in-EclipseLink
 */
package com.fractals.beans;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectCollectionMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

/**
 *
 * @author lynn
 */
public class LocalDateTimeAttributeConverter implements Converter {
	
    @Override
    public Object convertObjectValueToDataValue(Object o, Session sn) {
        LocalDateTime locDateTime = (LocalDateTime)o;
        return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
    }

    @Override
    public Object convertDataValueToObjectValue(Object o, Session sn) {
        Timestamp sqlTimestamp = (Timestamp)o;
        return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public void initialize(DatabaseMapping mapping, Session sn) {
        final DatabaseField field;
        if (mapping instanceof DirectCollectionMapping) {
            // handle @ElementCollection...
            field = ((DirectCollectionMapping) mapping).getDirectField();
        } else {
            field = mapping.getField();
        }

        field.setSqlType(java.sql.Types.TIMESTAMP);
    }
}
