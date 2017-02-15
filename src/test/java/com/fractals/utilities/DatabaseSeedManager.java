package com.fractals.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * Creates schema and seeds the database with the test data.
 * 
 * @author Alena Shulzhenko
 */
public class DatabaseSeedManager {
    private DataSource ds;
    // Default logger is java.util.logging
    private static final Logger log = Logger.getLogger("DatabaseSeedManager.class");

    /**
     * Initializes DatabaseSeedManager object.
     * @param ds DataSource object to open the connection.
     */
    public DatabaseSeedManager(DataSource ds) {
        this.ds = ds;
    }
    
    /**
     * Starts seeding database with test values.
     */
    public void seed() {
        final String seedDataScript = loadAsString("setup.sql");
        log.info("Seeding");
        
        try (Connection connection = ds.getConnection()) {
            for (String statement : splitStatements(new StringReader(seedDataScript), new String[] {";", "//"})) {
                //log.info("current line: " + statement);
                connection.prepareStatement(statement).execute();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed seeding database", e);
        }
    }
    
    /**
     * Loads database creation file and returns it as a String.
     * @param path The path to the database config file.
     * @return database config file contents as a String.
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path)) {
            return new Scanner(inputStream).useDelimiter("\\A").next();
        } 
        catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
        
    }

    /**
     * Splits database config file into list of sql statements.
     * @param reader Reader object.
     * @param delimiters Delimiters used in statements.
     * @return list of sql statements.
     */
    private List<String> splitStatements(Reader reader, String[] delimiters) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                for(String statementDelimiter : delimiters) {
                    if (line.endsWith(statementDelimiter)) {
                        statements.add(sqlStatement.toString());
                        sqlStatement.setLength(0);
                    }
                }
            }
            return statements;
        } 
        catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    /**
     * Determines whether given string is a comment.
     * @param line The line in the database config file to examine.
     * @return true if this line is a comment; false otherwise.
     */
    private boolean isComment(final String line) {
        return line.startsWith("--") || /*line.startsWith("//") ||*/ line.startsWith("/*");
    }
    
}
