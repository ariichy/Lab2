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
     * This method is for updating IndyWinners within the indywinners database
     * by specifying the year you want to update, and providing the updated
     * {@code IndyWinner} object.
     * @param year The year of the winner you are updating
     * @param winner The updated IndyWinner object
     */
    @Override
    public void updateWinner(int year, IndyWinner winner) {
        PreparedStatement stmt;
        try {
            winner.setYear(year);
            String sql = "UPDATE indywinners SET driver = ?, averagespeed = ?, country = ? WHERE year = ?";
            stmt = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stmt.setString(1, winner.getDriver());
            stmt.setDouble(2, winner.getAverageSpeed());
            stmt.setString(3, winner.getCountry());
            stmt.setInt(4, year);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    /**
     * This method is to delete {@code IndyWinner} objects from the database, done so
     * by specifying the year of the winner you want to delete.
     * @param year The year of the winner you want to remove.
     */
    @Override
    public void deleteWinner(int year) {
        try {
            String query = "DELETE FROM indywinners WHERE year = ?";
            PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement(query);
            stmt.setInt(1, year);
            stmt.executeUpdate();
            // ResultSet result = stmt.executeQuery(query);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    /**
     * This method is for inserting new {@code IndyWinner} objects into
     * the indywinners database.
     * @param winner The IndyWinner object with the attributes set
     */
    @Override
    public void insertWinner(IndyWinner winner) {

        PreparedStatement stmt;

        try {
            String sql = "INSERT INTO indywinners (year, driver, averagespeed, country) values (?, ?, ?, ?)";
            stmt = DBConnection.getInstance().getConnection().prepareStatement(sql);

            stmt.setInt(1,winner.getYear());
            stmt.setString(2, winner.getDriver());
            stmt.setDouble(3, winner.getAverageSpeed());
            stmt.setString(4, winner.getCountry());
            stmt.executeUpdate();

            System.out.println("Inserted Driver: " + winner);
        } catch (SQLException e) {

            e.printStackTrace();
        }
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
