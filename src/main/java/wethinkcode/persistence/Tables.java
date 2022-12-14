package wethinkcode.persistence;

import java.sql.*;

/**
 * Exercise 3.1
 */
public class Tables {
    private final Connection connection;
    static final String DB_URL = "jdbc:mysql://localhost/Genres";
    static final String USER = "guest";
    static final String PASS = "guest123";
    /**
     * Create an instance of the Tables object using the provided database connection
     * @param connection The JDBC connection to use
     */
    public Tables(Connection connection)

    {
        this.connection = connection;

    }

    /**
     * 3.1 Complete this method
     *
     * Create the Genres table
     * @return true if the table was successfully created, otherwise false
     */
    public boolean createGenres() {
        String genre = "CREATE TABLE Genres (code TEXT NOT NULL, description TEXT NOT NULL, PRIMARY KEY (code))";
        try (

                PreparedStatement stat = connection.prepareStatement(genre);
        ) {

            stat.executeUpdate();
//            System.out.println("Successfully Created");
            stat.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
//            System.out.print("failed to created");
            return false;
        }
    }

    /**
     * 3.1 Complete this method
     *
     * Create the Books table
     * @return true if the table was successfully created, otherwise false
     */
    public boolean createBooks() {
        createGenres();
        String books = "CREATE TABLE Books(\n" +
                "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    genre_code TEXT NOT NULL,\n" +
                "    FOREIGN KEY (genre_code) REFERENCES Genres(code)\n" +
                ");";
        try (
                PreparedStatement stat2 = connection.prepareStatement(books);
        ) {
            stat2.executeUpdate();
//            System.out.println("Successfully Created");
            stat2.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
//            System.out.printf("failed to created");
            return false;
        }

    }

    /**
     * 3.1 Complete this method
     *
     * Execute a SQL statement containing an SQL command to create a table.
     * If the SQL statement is not a create statement, it should return false.
     *
     * @param sql the SQL statement containing the create command
     * @return true if the command was successfully executed, else false
     */
    protected boolean createTable(String sql) {

//        sql = "CREATE TABLE MOCK";

        try {
            Statement stat3 = connection.createStatement();
            stat3.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sql.contains("CREATE TABLE")) {
            System.out.printf("success");
            return true;
        }else {
            System.out.printf("failed to create");
            return false;
        }
    }
}
