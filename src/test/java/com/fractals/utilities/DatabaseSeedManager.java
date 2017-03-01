package com.fractals.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * Creates schema and seeds the database with the test data.
 * The routine is courtesy of Bartosz Majsak.
 * 
 * @author Aline Shulzhenko
 */
public class DatabaseSeedManager {
    private DataSource ds;
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
        log.info("Seeding a schema");
        seedFromFile(loadAsString("schema.sql"));
        createTrigger();
        log.info("Adding test values");
        seedFromFile(loadAsString("setup.sql"));
    }
    
    /**
     * Creates a trigger on orders table.
     */
    private void createTrigger() {
        try (Connection connection = ds.getConnection()) {
            Statement stmt = connection.createStatement();
            log.info("Creating a trigger.");
            stmt.execute("drop trigger if exists before_orders_insert;");
            stmt.execute("create trigger before_orders_insert " 
                    + "before insert on orders " 
                    + "for each row begin "
                    + "declare provinceid int; declare psttax float; "
                    + "declare gsttax float; declare hsttax float; "
                    + "select province_id into provinceid from users where users.id = new.user_id; "
                    + "select pst, gst, hst into psttax, gsttax, hsttax from provinces where provinces.id = provinceid; "
                    + "set new.gross_cost = new.net_cost + new.net_cost*psttax + new.net_cost*gsttax + new.net_cost*hsttax; "
                    + "end; ");
            
            stmt.execute("drop trigger if exists before_orders_update;");
            stmt.execute("create trigger before_orders_update " 
                    + "before update on orders " 
                    + "for each row begin "
                    + "declare provinceid int; declare psttax float; "
                    + "declare gsttax float; declare hsttax float; "
                    + "select province_id into provinceid from users where users.id = new.user_id; "
                    + "select pst, gst, hst into psttax, gsttax, hsttax from provinces where provinces.id = provinceid; "
                    + "set new.gross_cost = new.net_cost + new.net_cost*psttax + new.net_cost*gsttax + new.net_cost*hsttax; "
                    + "end; ");
            
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed seeding database", e);
        }
    }
    
    /**
     * Seed to the database from the specified file.
     * @param file the file with sql statements.
     */
    private void seedFromFile(String file) {
        try (Connection connection = ds.getConnection()) {
            for (String statement : splitStatements(new StringReader(file), ";")) {
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
     * @param statementDelimiter Delimiter used in statements.
     * @return list of sql statements.
     */
    private List<String> splitStatements(Reader reader, String statementDelimiter) {
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
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
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
        return line.startsWith("--") || line.startsWith("//") || line.startsWith("/*");
    }
    
}
