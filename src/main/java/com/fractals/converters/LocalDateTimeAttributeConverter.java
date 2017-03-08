/*
 * The courtesy of https://github.com/ancoron/pg-inet-maven/wiki/Support-custom-data-types-in-EclipseLink
 */
package com.fractals.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectCollectionMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

/**
 * Converts java.sql.Timestamp to LocalDateTime object.
 *
 * @author Aline Shulzhenko
 * @version 19/02/2017
 * @since 1.8
 */
public class LocalDateTimeAttributeConverter implements Converter {
	
    /**
     * Converts LocalDateTime to sql Timestamp.
     * @param o LocalDateTime object.
     * @param sn Session.
     * @return Timestamp sql object.
     */
    @Override
    public Object convertObjectValueToDataValue(Object o, Session sn) {
        LocalDateTime locDateTime = (LocalDateTime)o;
        System.out.println(locDateTime);
        return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
    }

    /**
     * Converts Timestamp sql to LocalDateTime.
     * @param o java.sql.Timestamp object.
     * @param sn Session
     * @return LocalDateTime object.
     */
    @Override
    public Object convertDataValueToObjectValue(Object o, Session sn) {
        Timestamp sqlTimestamp = (Timestamp)o;
        System.out.println(sqlTimestamp);
        return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
    }

    /**
     * Returns if the object is mutable.
     * @return false as this object should be immutable.
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     * Initializes the converter.
     * @param mapping DatabaseMapping object.
     * @param sn Session.
     */
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
