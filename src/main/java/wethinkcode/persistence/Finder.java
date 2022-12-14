package wethinkcode.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 3.3
 */
public class Finder {

    private final Connection connection;

    /**
     * Create an instance of the Finder object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public Finder(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.3 (part 1) Complete this method
     * <p>
     * Finds all genres in the database
     *
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findAllGenres() throws SQLException {
            List<Genre> genres = new ArrayList<>();
            String sql = "SELECT * FROM Genres";

            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String code = results.getString("code");
                String description = results.getString("description");
                Genre genre = new Genre(code,description);
                genres.add(genre);
            }

            return genres;
        }



    /**
     * 3.3 (part 2) Complete this method
     * <p>
     * Finds all genres in the database that have specific substring in the description
     *
     * @param pattern The pattern to match
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findGenresLike(String pattern) throws SQLException {
        String sql = "SELECT * FROM GENRES WHERE description LIKE ? ;";
        List<Genre> genres = new ArrayList<>();
        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setString(1,pattern);
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            String code = resultSet.getString("code");
            String description = resultSet.getString("description");
            Genre genre = new Genre(code,description);
            genres.add(genre);
        }
        return genres;
    }

    /**
     * 3.3 (part 3) Complete this method
     * <p>
     * Finds all books with their genres
     *
     * @return a list of `BookGenreView` objects
     * @throws SQLException the query failed
     */
    public List<BookGenreView> findBooksAndGenres() throws SQLException {
        String sql = "SELECT title, description FROM Books INNER JOIN Genres ON Books.genre_code = Genres.code ;";

        List<BookGenreView> booksGenres = new ArrayList<>();

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            String bookTitle = resultSet.getString("title");
            String genreDescription = resultSet.getString("description");
            BookGenreView bookGenreView = new BookGenreView(bookTitle,genreDescription);
            booksGenres.add(bookGenreView);
        }

        return booksGenres;
    }

    /**
     * 3.3 (part 4) Complete this method
     * <p>
     * Finds the number of books in a genre
     *
     * @return the number of books in the genre
     * @throws SQLException the query failed
     */
    public int findNumberOfBooksInGenre(String genreCode) throws SQLException {
        String sql = "SELECT count(genre_code) FROM Books WHERE genre_code = ?;";

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setString(1,genreCode);
        ResultSet resultSet = ps.executeQuery();

        return resultSet.getInt(1);
    }
}
