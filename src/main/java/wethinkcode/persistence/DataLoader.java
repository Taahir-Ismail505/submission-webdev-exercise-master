package wethinkcode.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Exercise 3.2
 */
public class DataLoader {
    private final Connection connection;

    /**
     * These are the Genres that must be persisted to the database
     */
    private final Map<String, Genre> genres = Map.of(
            "PROG", new Genre("PROG", "Programming"),
            "BIO", new Genre("BIO", "Biography"),
            "SCIFI", new Genre("SCIFI", "Science Fiction"));

    /**
     * These are the Books that must be persisted to the database
     */
    private final List<Book> books = List.of(
            new Book("Test Driven Development", genres.get("PROG")),
            new Book("Programming in Haskell", genres.get("PROG")),
            new Book("Scatterlings of Africa", genres.get("BIO")));

    /**
     * Create an instance of the DataLoader object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public DataLoader(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.2 (part 1) Complete this method
     * <p>
     * Inserts data from the `Genres` collection into the `Genres` table.
     *
     * @return true if the data was successfully inserted, otherwise false
     */
    public boolean insertGenres() {
        genres.values();
        String sql = "INSERT INTO Genres(code,description)" +
                "VALUES (?, ?);";

            for (Genre genre : genres.values()) {
            String code = genre.getCode();
            String description = genre.getDescription();

            PreparedStatement ps = null;
            try {
                ps = this.connection.prepareStatement(sql);
                ps.setString(1, code);
                ps.setString(2, description);

                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 3.2 (part 1) Complete this method
     * <p>
     * Inserts data from the `Books` collection into the `Books` table.
     *
     * @return true if the data was successfully inserted, otherwise false
     */
    public List<Book> insertBooks() throws SQLException {
        String sql = "INSERT INTO Books(title,genre_code)" +
                "VALUES (?, ?);";
        List<Book> newBooks = new ArrayList<>();
        for (Book book : books) {

            String title = book.getTitle();
            String genre_code = book.getGenre().getCode();

            book.assignId(books.indexOf(book));

            PreparedStatement ps = null;
            try {
                ps = this.connection.prepareStatement(sql);
                ps.setString(1, title);
                ps.setString(2, genre_code);

                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return books;
    }

    /**
     * Get the last id generated from the prepared statement
     *
     * @param s the prepared statement
     * @return the last id generated
     * @throws SQLException if the id was not generated
     */
    private int getGeneratedId(PreparedStatement s) throws SQLException {
        ResultSet generatedKeys = s.getGeneratedKeys();
        if (!generatedKeys.next()) throw new SQLException("Id was not generated");
        return generatedKeys.getInt(1);
    }
}


