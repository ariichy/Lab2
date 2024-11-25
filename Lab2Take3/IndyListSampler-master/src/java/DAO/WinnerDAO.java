package DAO;

import Model.IndyWinner;

import java.sql.SQLException;
import java.util.List;

/**
 * This interface describes the blueprint that implementing classes must meet.
 * It contains a method to return all the winners from the database, and a
 * method to retrieve the total row count from the database.
 *
 * @author Austin Richardson
 */
public interface WinnerDAO {

    /**
     * This method signature represents a method that should retrieve all
     * {@code IndyWinner} objects from the database and put them into a {@code List}. The
     * parameters offset and limit are to aid in pagination.
     * @param offset The page number to start from.
     * @param limit The number of items per page to show.
     * @return {@code List<IndyWinner>} - a list of the IndyWinners from the
     * database.
     * @throws SQLException
     */
    List<IndyWinner> getWinners(int offset, int limit) throws SQLException;

    /**
     * This method represents a way to get the total row count from the database
     * and return it as an int.
     * @return The total row count from the database.
     * @throws SQLException
     */
    int getTotalCount() throws SQLException;

    /**
     * This method is to be used for inserting an Indy Winner into the database
     * @param winner The IndyWinner object with the attributes set
     */
    void insertWinner(IndyWinner winner);

    /**
     * This method is used to update an existing winner by setting its attributes
     * and specifying the year you want to update.
     * @param year The year of the winner you are updating
     * @param winner The updated IndyWinner object
     */
    void updateWinner(int year, IndyWinner winner);

    /**
     * This method is for deleting IndyWinners from the database, done so
     * by specifying the year of the winner you wish to delete.
     * @param year The year of the winner you want to remove.
     */
    void deleteWinner(int year);
}
