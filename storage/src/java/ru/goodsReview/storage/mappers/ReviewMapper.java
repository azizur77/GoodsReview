package ru.goodsReview.storage.mappers;

import ru.goodsReview.core.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 * User: Artemij
 * Date: 19.10.11
 * Time: 1:21
 */
public class ReviewMapper implements ParameterizedRowMapper<Review> {
    public Review mapRow(ResultSet resultSet, int i) {
        try {
            return new Review(
                    resultSet.getLong("id"),
                    resultSet.getLong("product_id"),
                    resultSet.getString("content"),
                    resultSet.getString("author"),
                    resultSet.getLong("date"),
                    resultSet.getString("description"),
                    resultSet.getLong("source_id"),
                    resultSet.getString("source_url"),
                    resultSet.getDouble("positivity"),
                    resultSet.getDouble("importance"),
                    resultSet.getInt("vote_yes"),
                    resultSet.getInt("vote_no"));
        } catch (SQLException e) {
            // Something is wrong with the base, i.e. one of column labels isn't presented.
            return new Review(-1, -1, "NOCONTENT", "NOAUTHOR", 0, "", -1, "", 0.0, 0.0, 0, 0);
        }
    }
}
