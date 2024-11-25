package Model;

/**
 * This class is responsible for the creation of concrete {@code IndyWinner}'s,
 * as well as accessing its information through getters.
 *
 * @author Austin Richardson
 */
public class IndyWinner {
    private int year; // year the driver won
    private String driver; // driver name
    private double averageSpeed; // average car speed
    private String country; // country the driver is from

    /**
     * Constructor for the {@code IndyWinner} object that requires all
     * properties to be provided.
     * @param year year the driver won
     * @param driver name of the driver
     * @param averageSpeed average car speed
     * @param country country the driver is from
     */
    public IndyWinner(int year, String driver, double averageSpeed, String country) {
        this.year = year;
        this.driver = driver;
        this.averageSpeed = averageSpeed;
        this.country = country;
    }

    /*
    Getters for the IndyWinner attributes
     */
    public int getYear() {
        return year;
    }

    public String getDriver() {
        return driver;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public String getCountry() {
        return country;
    }

    /**
     * This {@code toString()} method returns a formatted string
     * for easy interpretation of the {@code IndyWinner} information.
     * @return all attributes of the {@code IndyWinner}
     */
    @Override
    public String toString() {
        return "Year: " + year + ", Driver: " + driver +
                ", Average Speed: " + averageSpeed +
                ", Country: " + country;
    }
}