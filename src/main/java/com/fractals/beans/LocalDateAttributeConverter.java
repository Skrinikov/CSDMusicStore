/*
 * The courtesy of https://github.com/ancoron/pg-inet-maven/wiki/Support-custom-data-types-in-EclipseLink
 */
package com.fractals.beans;

import java.sql.Date;
import java.time.LocalDate;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectCollectionMapping;
import org.eclipse.persistence.sessions.Session;

/**
 *
 * @author lynn
 */
public class LocalDateAttributeConverter implements Converter {
	
    @Override
    public Object convertObjectValueToDataValue(Object o, Session sn) {
        LocalDate locDate = (LocalDate)o;
        return (locDate == null ? null : Date.valueOf(locDate));
    }

    @Override
    public Object convertDataValueToObjectValue(Object o, Session sn) {
        Date sqlDate = (Date)o;
        return (sqlDate == null ? null : sqlDate.toLocalDate());
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

        field.setSqlType(java.sql.Types.DATE);
    }
}
