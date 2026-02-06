package com.biblioteca.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.config.DBManager;
import com.biblioteca.model.Author;
import com.biblioteca.model.Book;
import com.biblioteca.model.Genre;
import com.biblioteca.model.Publisher;
import com.biblioteca.utils.colors;

public class BookRepositoryImpl implements BookRepository {

    @Override
    public void addBook(Book book) {
        String insertBook = "INSERT INTO books (title, isbn, description, id_publisher) VALUES (?,?,?,?) RETURNING id_book";

        try (Connection con = DBManager.getConnection()) {
            con.setAutoCommit(false);

            int publisherId = getOrCreatePublisher(con, book.getPublisher().getName());

            int bookId;
            try (PreparedStatement ps = con.prepareStatement(insertBook)) {
                ps.setString(1, book.getTitle());
                ps.setString(2, book.getIsbn());
                ps.setString(3, book.getDescription());
                ps.setInt(4, publisherId);
                ResultSet rs = ps.executeQuery();
                rs.next();
                bookId = rs.getInt(1);
            }

            for (Author a : book.getAuthors()) {
                int authorId = getOrCreateAuthor(con, a.getName());

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO books_authors (id_book, id_author) VALUES (?, ?)")) {
                    ps.setInt(1, bookId);
                    ps.setInt(2, authorId);
                    ps.executeUpdate();
                }
            }

            for (Genre g : book.getGenres()) {
                int genreId = getOrCreateGenre(con, g.getGenre());

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO books_genres (id_book, id_genre) VALUES (?, ?)")) {
                    ps.setInt(1, bookId);
                    ps.setInt(2, genreId);
                    ps.executeUpdate();
                }
            }

            con.commit();

        } catch (SQLException e) {
            throw new RuntimeException(colors.RED + "Error al insertar el libro completo" + colors.RESET, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = """
                    SELECT
                        b.id_book,
                        b.title,
                        b.isbn,
                        b.description,
                        p.name AS publisher,
                        STRING_AGG(DISTINCT a.name, ', ') AS authors,
                        STRING_AGG(DISTINCT g.genre, ', ') AS genres
                    FROM books b
                    JOIN publishers p ON b.id_publisher = p.id_publisher
                    JOIN books_authors ba ON b.id_book = ba.id_book
                    JOIN authors a ON ba.id_author = a.id_author
                    JOIN books_genres bg ON b.id_book = bg.id_book
                    JOIN genres g ON bg.id_genre = g.id_genre
                    GROUP BY b.id_book, b.title, b.isbn, b.description, p.name
                    ORDER BY b.title
                """;

        try (Connection con = DBManager.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Publisher publisher = new Publisher();
                publisher.setName(rs.getString("publisher"));
                Author author = new Author();
                author.setName(rs.getString("authors"));
                Genre genre = new Genre();
                genre.setGenre(rs.getString("genres"));
                Book book = new Book();
                book.setId(rs.getInt("id_book"));
                book.setTitle(rs.getString("title"));
                book.setIsbn(rs.getString("isbn"));
                book.setDescription(rs.getString("description"));
                book.setPublisher(publisher);
                book.setAuthors(List.of(author));
                book.setGenres(List.of(genre));
                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(colors.RED + "Error al listar libros" + colors.RESET, e);
        }
        return books;
    }

    @Override
    public void updateBook(Book book) {
        String updateBook = "UPDATE books SET title = ?, isbn = ?, description = ? WHERE id_book = ?";

        try (Connection con = DBManager.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(updateBook)) {
                ps.setString(1, book.getTitle());
                ps.setString(2, book.getIsbn());
                ps.setString(3, book.getDescription());
                ps.setInt(4, book.getId());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement("DELETE FROM books_authors WHERE id_book = ?")) {
                ps.setInt(1, book.getId());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement("DELETE FROM books_genres WHERE id_book = ?")) {
                ps.setInt(1, book.getId());
                ps.executeUpdate();
            }

            for (Author a : book.getAuthors()) {
                int authorId = getOrCreateAuthor(con, a.getName());

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO books_authors (id_book, id_author) VALUES (?, ?)")) {
                    ps.setInt(1, book.getId());
                    ps.setInt(2, authorId);
                    ps.executeUpdate();
                }
            }

            for (Genre g : book.getGenres()) {
                int genreId = getOrCreateGenre(con, g.getGenre());

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO books_genres (id_book, id_genre) VALUES (?, ?)")) {
                    ps.setInt(1, book.getId());
                    ps.setInt(2, genreId);
                    ps.executeUpdate();
                }
            }

            con.commit();

        } catch (SQLException e) {
            throw new RuntimeException(colors.RED + "Error al actualizar libro" + colors.RESET, e);
        }
    }

    @Override
    public void deleteById(int id) {
        String deleteBookAuthors = "DELETE FROM books_authors WHERE id_book = ?";
        String deleteBookGenres = "DELETE FROM books_genres WHERE id_book = ?";
        String deleteBook = "DELETE FROM books WHERE id_book = ?";

        try (Connection con = DBManager.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(deleteBookAuthors)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(deleteBookGenres)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            int rowsAffected;
            try (PreparedStatement ps = con.prepareStatement(deleteBook)) {
                ps.setInt(1, id);
                rowsAffected = ps.executeUpdate();
            }

            if (rowsAffected == 0) {
                System.out.println(colors.YELLOW + "No se encontró ningún libro con ID " + id + colors.RESET);
            } else {
                System.out.println(colors.GREEN + "Libro eliminado correctamente con ID " + id + colors.RESET);
            }

            con.commit();

        } catch (SQLException e) {
            throw new RuntimeException(colors.RED + "Error al eliminar libro con ID " + id + colors.RESET, e);
        }
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = """
                    SELECT
                        b.id_book,
                        b.title,
                        b.isbn,
                        b.description,
                        p.name AS publisher,
                        STRING_AGG(DISTINCT a.name, ', ') AS authors,
                        STRING_AGG(DISTINCT g.genre, ', ') AS genres
                    FROM books b
                    JOIN publishers p ON b.id_publisher = p.id_publisher
                    JOIN books_authors ba ON b.id_book = ba.id_book
                    JOIN authors a ON ba.id_author = a.id_author
                    JOIN books_genres bg ON b.id_book = bg.id_book
                    JOIN genres g ON bg.id_genre = g.id_genre
                    WHERE LOWER(TRIM(b.title)) LIKE LOWER(TRIM(?))
                    GROUP BY b.id_book, b.title, b.isbn, b.description, p.name
                    ORDER BY b.title
                """;

        try (Connection connection = DBManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Author author = new Author();
                    author.setName(rs.getString("authors"));
                    Publisher publisher = new Publisher();
                    publisher.setName(rs.getString("publisher"));
                    Genre genre = new Genre();
                    genre.setGenre(rs.getString("genres"));
                    Book book = new Book();
                    book.setId(rs.getInt("id_book"));
                    book.setTitle(rs.getString("title"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setDescription(rs.getString("description"));
                    book.setAuthors(List.of(author));
                    book.setPublisher(publisher);
                    book.setGenres(List.of(genre));
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    colors.RED + "Error al buscar libros por título: " + colors.RESET + e.getMessage(), e);
        }
        return books;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        String sql = """
                    SELECT
                        b.id_book,
                        b.title,
                        b.isbn,
                        b.description,
                        p.name AS publisher,
                        STRING_AGG(DISTINCT a.name, ', ') AS authors,
                        STRING_AGG(DISTINCT g.genre, ', ') AS genres
                        FROM books b
                        JOIN publishers p ON b.id_publisher = p.id_publisher
                        JOIN books_authors ba ON b.id_book = ba.id_book
                        JOIN authors a ON ba.id_author = a.id_author
                        JOIN books_genres bg ON b.id_book = bg.id_book
                        JOIN genres g ON bg.id_genre = g.id_genre
                        WHERE EXISTS (
                        SELECT 1
                        FROM books_authors ba2
                        JOIN authors a2 ON ba2.id_author = a2.id_author
                        WHERE ba2.id_book = b.id_book
                        AND a2.name ILIKE ?
                    )
                    GROUP BY b.id_book, b.title, b.isbn, b.description, p.name
                    ORDER BY b.title;
                """;

        try (Connection connection = DBManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + author + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Author a = new Author();
                    a.setName(rs.getString("authors"));
                    Publisher publisher = new Publisher();
                    publisher.setName(rs.getString("publisher"));
                    Genre g = new Genre();
                    g.setGenre(rs.getString("genres"));
                    Book book = new Book();
                    book.setId(rs.getInt("id_book"));
                    book.setTitle(rs.getString("title"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setDescription(rs.getString("description"));
                    book.setAuthors(List.of(a));
                    book.setPublisher(publisher);
                    book.setGenres(List.of(g));
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    colors.RED + "Error al buscar libros por autor: " + colors.RESET + e.getMessage(), e);
        }
        return books;
    }

    @Override
    public List<Book> findByGenre(String genre) {
        List<Book> books = new ArrayList<>();
        String sql= "SELECT b.id_book, b.title, b.isbn, p.name AS publisher, " +
             "STRING_AGG(DISTINCT a.name, ', ') AS authors, " +
             "STRING_AGG(DISTINCT g.genre, ', ') AS genres " +
             "FROM books b " +
             "JOIN publishers p ON b.id_publisher = p.id_publisher " +
             "JOIN books_authors ba ON b.id_book = ba.id_book " +
             "JOIN authors a ON ba.id_author = a.id_author " +
             "JOIN books_genres bg ON b.id_book = bg.id_book " +
             "JOIN genres g ON bg.id_genre = g.id_genre " +
             "WHERE EXISTS ( " +
             "  SELECT 1 FROM books_genres bg2 " +
             "  JOIN genres g2 ON bg2.id_genre = g2.id_genre " +
             "  WHERE bg2.id_book = b.id_book " +
             "  AND g2.genre ILIKE ? " +
             ") " +
             "GROUP BY b.id_book, b.title, b.isbn, p.name " +
             "ORDER BY b.title";


        try (Connection connection = DBManager.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + genre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Author author = new Author();
                    author.setName(rs.getString("authors"));
                    Publisher publisher = new Publisher();
                    publisher.setName(rs.getString("publisher"));
                    Genre g = new Genre();
                    g.setGenre(rs.getString("genres"));
                    Book book = new Book();
                    book.setId(rs.getInt("id_book"));
                    book.setTitle(rs.getString("title"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setAuthors(List.of(author));
                    book.setPublisher(publisher);
                    book.setGenres(List.of(g));
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    colors.RED + "Error al buscar libros por género: " + colors.RESET + e.getMessage(), e);
        }

        return books;
    }

    @Override
    public boolean bookExistsByIsbn(Connection con, String isbn) {
        String sql = "SELECT 1 FROM books WHERE isbn = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, isbn);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getOrCreatePublisher(Connection con, String name) {
        String select = "SELECT id_publisher FROM publishers WHERE name = ?";
        try (PreparedStatement ps = con.prepareStatement(select)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String insert = "INSERT INTO publishers (name) VALUES (?) RETURNING id_publisher";
        try (PreparedStatement ps = con.prepareStatement(insert)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getOrCreateAuthor(Connection con, String name) {
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT id_author FROM authors WHERE name = ?")) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO authors (name) VALUES (?) RETURNING id_author")) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void linkBookAuthor(Connection con, int bookId, int authorId) {
        String sql = """
                INSERT INTO books_authors (id_book, id_author)
                VALUES (?, ?)
                ON CONFLICT DO NOTHING
                """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.setInt(2, authorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getOrCreateGenre(Connection con, String name) {
        String select = "SELECT id_genre FROM genres WHERE genre = ?";
        try (PreparedStatement ps = con.prepareStatement(select)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rs.getInt("id_genre");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar el género", e);
        }
        String insert = "INSERT INTO genres (genre) VALUES (?) RETURNING id_genre";
        try (PreparedStatement ps = con.prepareStatement(insert)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("id_genre");
            }
        } catch (SQLException e) {
            throw new RuntimeException(colors.RED + "Error al insertar el género" + colors.RESET, e);
        }
    }

    @Override
    public void linkBookGenre(Connection con, int bookId, int genreId) {
        String sql = """
                INSERT INTO books_genres (id_book, id_genre)
                VALUES (?, ?)
                ON CONFLICT DO NOTHING
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.setInt(2, genreId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(colors.RED + "Error al vincular libro con género" + colors.RESET, e);
        }
    }

    @Override
    public Book findById(int id) {
        String sql = """
                    SELECT
                        b.id_book,
                        b.title,
                        b.isbn,
                        b.description,
                        p.id_publisher,
                        p.name AS publisher
                    FROM books b
                    JOIN publishers p ON b.id_publisher = p.id_publisher
                    WHERE b.id_book = ?
                """;

        try (Connection con = DBManager.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Book book = new Book();
                book.setId(rs.getInt("id_book"));
                book.setTitle(rs.getString("title"));
                book.setIsbn(rs.getString("isbn"));
                book.setDescription(rs.getString("description"));
                Publisher publisher = new Publisher();
                publisher.setId(rs.getInt("id_publisher"));
                publisher.setName(rs.getString("publisher"));
                book.setPublisher(publisher);

                String sqlAuthors = """
                            SELECT a.id_author, a.name
                            FROM authors a
                            JOIN books_authors ba ON a.id_author = ba.id_author
                            WHERE ba.id_book = ?
                        """;

                try (PreparedStatement psAuth = con.prepareStatement(sqlAuthors)) {
                    psAuth.setInt(1, id);
                    try (ResultSet rsAuth = psAuth.executeQuery()) {
                        List<Author> authors = new ArrayList<>();
                        while (rsAuth.next()) {
                            Author a = new Author();
                            a.setId(rsAuth.getInt("id_author"));
                            a.setName(rsAuth.getString("name"));
                            authors.add(a);
                        }
                        book.setAuthors(authors);
                    }
                }

                String sqlGenres = """
                            SELECT g.id_genre, g.genre
                            FROM genres g
                            JOIN books_genres bg ON g.id_genre = bg.id_genre
                            WHERE bg.id_book = ?
                        """;

                try (PreparedStatement psGenre = con.prepareStatement(sqlGenres)) {
                    psGenre.setInt(1, id);
                    try (ResultSet rsGenre = psGenre.executeQuery()) {
                        List<Genre> genres = new ArrayList<>();
                        while (rsGenre.next()) {
                            Genre g = new Genre();
                            g.setId(rsGenre.getInt("id_genre"));
                            g.setGenre(rsGenre.getString("genre"));
                            genres.add(g);
                        }
                        book.setGenres(genres);
                    }
                }

                return book;
            }

        } catch (Exception e) {
            throw new RuntimeException(colors.RED + "Error al obtener libro por ID " + colors.RESET, e);
        }
    }
}
