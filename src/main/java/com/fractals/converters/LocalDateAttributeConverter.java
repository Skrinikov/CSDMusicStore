/*
 * The courtesy of https://github.com/ancoron/pg-inet-maven/wiki/Support-custom-data-types-in-EclipseLink
 */
package com.fractals.converters;

import java.sql.Date;
import java.time.LocalDate;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectCollectionMapping;
import org.eclipse.persistence.sessions.Session;

/**
 * Converts java.sql.Date to LocalDate object.
 *
 * @author Aline Shulzhenko
 * @version 19/02/2017
 * @since 1.8
 */
public class LocalDateAttributeConverter implements Converter {
	
    /**
     * Converts LocalDate to sql Date.
     * @param o LocalDate object.
     * @param sn Session.
     * @return Date sql object.
     */
    @Override
    public Object convertObjectValueToDataValue(Object o, Session sn) {
        LocalDate locDate = (LocalDate)o;
        return (locDate == null ? null : Date.valueOf(locDate));
    }

    /**
     * Converts Date sql to LocalDate.
     * @param o java.sql.Date object.
     * @param sn Session
     * @return LocalDate object.
     */
    @Override
    public Object convertDataValueToObjectValue(Object o, Session sn) {
        Date sqlDate = (Date)o;
        return (sqlDate == null ? null : sqlDate.toLocalDate());
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

        field.setSqlType(java.sql.Types.DATE);
    }
}
