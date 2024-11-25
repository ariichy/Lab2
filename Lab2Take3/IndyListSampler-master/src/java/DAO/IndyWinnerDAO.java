package DAO;

import Database.DBConnection;
import Model.IndyWinner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is responsible for transferring data to and from the database.
 * It implements a {@code getWinners()} method that returns a list of winners,
 * and a {@code getTotalCount()} method that returns the total count of the database
 * rows.
 *
 * @author Austin Richardson
 */
public class IndyWinnerDAO implements WinnerDAO {

    /**
     * Fetches all the indy winners information from the database
     * and takes offset and limit parameters to aid in pagination
     * @param offset  the starting point for pagination
     * @param limit  the limit of rows to retrieve
     * @return the list of indy winners
     * @throws SQLException
     */
    public List<IndyWinner> getWinners(int offset, int limit) throws SQLException {
        List<IndyWinner> winners = new ArrayList<>();

        String query = "SELECT year, driver, averageSpeed, country FROM IndyWinners ORDER BY year DESC LIMIT ? OFFSET ?";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int year = rs.getInt("year");
                    String driver = rs.getString("driver");
                    double averageSpeed = rs.getDouble("averageSpeed");
                    String country = rs.getString("country");
                    winners.add(new IndyWinner(year, driver, averageSpeed, country));
                }
            }
        }

        return winners;
    }

    /**
     * This method executes a query to retrieve the count of rows in
     * the indywinners table.
     * @return the number of rows in the indywinners table
     * @throws SQLException
     */
    public int getTotalCount() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM IndyWinners";
        try (Connection con = DBConnection.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()){
                return rs.getInt("count");
            }
        }
        return 0;
    }
}
